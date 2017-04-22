package ng.jifudaily.support.container;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import javax.inject.Inject;

/**
 * Created by Ng on 2017/4/21.
 */

public class ContainerSwitcher {

    private ContainerManager containerManager;

    public ContainerSwitcher bind(ContainerManager containerManager) {
        this.containerManager = containerManager;
        return this;
    }

    public ContainerSwitcherSource switchFrom(Container from) {
        return new ContainerSwitcherSource(from);
    }

    public class ContainerSwitcherSource {
        private Container source;

        private ContainerSwitcherSource(Container source) {
            this.source = source;
        }

        public Container to(Container to) {
            return to(to, true);
        }

        public Container to(Container to, boolean disposePrevious) {

            View showingView = source.getContainingView();
            View toShowView = to.getContainingView();
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
