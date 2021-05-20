package go.meethour.io.react.sdk;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNMeetHourPackage implements ReactPackage, IRNMeetHourViewReference {

    private RNMeetHourView mMeetHourView = null;

    public void setMeetHourView(RNMeetHourView meetHourView) {
        mMeetHourView = meetHourView;
    }

    public RNMeetHourView getMeetHourView() {
        return mMeetHourView;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new RNMeetHourModule(reactContext, this));
        return modules;
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new RNMeetHourViewManager(reactContext, this)
        );
    }
}
