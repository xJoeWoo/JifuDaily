package ng.jifudaily.support.container;

import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.Stack;

import ng.jifudaily.view.base.ContainerLayout;

/**
 * Created by Ng on 2017/4/21.
 */

public final class ContainerSwitcher {

    private ContainerManager containerManager;
    private Stack<Container> stack;
    private ContainerLayout rootLayout;

    public ContainerSwitcher manager(ContainerManager containerManager) {
        this.containerManager = containerManager;
        return this;
    }

    public boolean back() {
        return back(true);
    }

    public boolean back(boolean disposePrevious) {
        Stack<Container> stack = getContainersStack();
        if (stack.size() < 2) {
            return false;
        }
        performSwitch(null, true, disposePrevious, false);
        return true;
    }

    public Container to(Container target) {
        return to(target, true, false);
    }

    public Container to(Container target, boolean disposePrevious, boolean createNewView) {
        return performSwitch(target, false, disposePrevious, createNewView);
    }


    private Container performSwitch(Container target, boolean isBack, boolean disposePrevious, boolean createNewView) {
        Stack<Container> stack = getContainersStack();
        Container source = null;

        if (!stack.isEmpty()) {
            if (isBack) {
                source = stack.pop();
                target = stack.peek();
            } else {
                source = stack.peek();

            }
            if (source == target) { // same as target
                return target;
            }
        }

        View toShowView = createNewView || target.isRequestNewView() ? target.createContainingView() : target.getContainingView();

        View showingView = null;
        ViewParent showingViewParent = null;
        TransitionSet transitionSet = new TransitionSet();
        if (source != null) {
            transitionSet.addTransition(source.getExitTransition());
            showingView = source.getContainingView();
            showingViewParent = showingView.getParent();
            source.onBeforeDetach();
        }
        target.onBeforeAttach();

        transitionSet.addTransition(isBack ? target.getReenterTransition() : target.getEnterTransition());

        if (showingViewParent != null && !(showingViewParent instanceof ContainerLayout)) {
            ViewGroup parent = (ViewGroup) showingViewParent;
            // other views not contained by default container_news_content_root view
            int index = parent.indexOfChild(showingView);
            parent.removeView(showingView);
            TransitionManager.beginDelayedTransition(parent, transitionSet);
            parent.addView(toShowView, index);
        } else {
            ViewParent parent = showingViewParent;
            if (parent == null) {
                containerManager.getActivity().setContentView(getDefaultRootLayout());
                parent = getDefaultRootLayout();
            }
            ViewGroup viewGroupParent = (ViewGroup) parent;
            TransitionManager t = new TransitionManager();
            Scene sTo = new Scene(viewGroupParent, toShowView);
            t.setTransition(sTo, transitionSet);
            if (showingView != null) {
                Scene sFrom = new Scene((ViewGroup) parent, showingView);
                t.setTransition(sFrom, sTo, transitionSet);
            }

            // when quickly press back source view haven't finished anim,
            // the toShowView will have an "animation overlay view" parent,
            // need to remove it first, or entire app will crash due to "The specified child already has a parent"
            if (toShowView.getParent() != null) {
                ((ViewGroup) toShowView.getParent()).removeView(toShowView);
            }


            t.transitionTo(sTo);

        }

        target.setAttached(true);
        target.onAttached();

        if (source != null) {
            source.setAttached(false);
            source.onDetached();
            if (disposePrevious && source.shouldDispose()) {
                source.disposeView();
            }
        }

        if (!isBack) {
            stack.push(target);
        }

        return target;
    }

    private ContainerLayout getDefaultRootLayout() {
        if (rootLayout == null) {
            rootLayout = new ContainerLayout(containerManager.getActivity());
        }
        return rootLayout;
    }


    private Stack<Container> getContainersStack() {
        if (stack == null) {
            stack = new Stack<>();
        }
        return stack;
    }


}
