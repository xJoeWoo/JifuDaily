package ng.jifudaily.support.ioc.service;

import javax.inject.Inject;

import dagger.Lazy;
import ng.jifudaily.support.util.UnitTool;

/**
 * Created by Ng on 2017/4/27.
 */

public class ToolService {

    private Lazy<UnitTool> dpUtilLazy;

    @Inject
    public ToolService(Lazy<UnitTool> dpUtilLazy) {
        this.dpUtilLazy = dpUtilLazy;
    }

    public UnitTool unit() {
        return dpUtilLazy.get();
    }
}
