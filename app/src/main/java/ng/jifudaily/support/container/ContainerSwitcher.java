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
            ViewParent root = showingViewParent;
            if (root == null) {
                containerManager.getActivity().setContentView(getDefaultRootLayout());
                root = getDefaultRootLayout();
            }
            Scene sTo = new Scene((ViewGroup) root, toShowView);
//            if (showingView != null) {
//                Scene sFrom = new Scene((ViewGroup) container_news_content_root, showingView);
//                TransitionManager t = new TransitionManager();
//                t.setTransition(sFrom, sTo, transitionSet);
//                t.transitionTo(sTo);
//            } else {
            TransitionManager.go(sTo, transitionSet);

//            }
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
