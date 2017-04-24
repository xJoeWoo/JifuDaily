package ng.jifudaily.support.container;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.Stack;

import javax.inject.Inject;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerSwitcher {

    private ContainerManager containerManager;
    private Stack<Container> stack;


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
        switchFrom(stack.pop()).to(stack.peek(), disposePrevious, false);
        return true;
    }

    /**
     * if null, will call <tt>Activity#setContent</tt>
     *
     * @param from
     * @return
     */
    private ContainerSwitcherSource switchFrom(Container from) {
        return new ContainerSwitcherSource(from);
    }

    public Container switchTo(Container to) {
        return switchTo(to, true);
    }

    public Container switchTo(Container to, boolean disposePrevious) {
        if (getContainersStack().isEmpty()) {
            return switchFrom(null).to(to, disposePrevious, true);
        }
        return switchFrom(getContainersStack().peek()).to(to, disposePrevious, true);
    }

    private Stack<Container> getContainersStack() {
        if (stack == null) {
            stack = new Stack<>();
        }
        return stack;
    }

    public class ContainerSwitcherSource {
        private Container source;

        private ContainerSwitcherSource(Container source) {
            this.source = source;
        }

        public Container to(Container to) {
            return to(to, true, true);
        }

        public Container to(Container to, boolean disposePrevious, boolean saveToStack) {

            if (source == to) {
                return to;
            }

            if (saveToStack) {
                getContainersStack().push(to);
            }

            View toShowView = to.getContainingView();

            if (source == null) {
                containerManager.getActivity().setContentView(toShowView);
                return to;
            }

            View showingView = source.getContainingView();
            ViewParent showingViewParent = showingView.getParent();

            to.onBeforeAttach();

            if (showingViewParent == null) { // showing view is root view
                containerManager.getActivity().setContentView(toShowView);
            } else {
                ViewGroup p = (ViewGroup) showingViewParent;
                int index = p.indexOfChild(showingView);
                p.removeView(showingView);
                p.addView(toShowView, index);
            }

            if (source != null) {
                source.onDetached();
                if (disposePrevious) {
                    source.dispose();
                }
            }
            to.onAttached();

            return to;
        }

    }
}
