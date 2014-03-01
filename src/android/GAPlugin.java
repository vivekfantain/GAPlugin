package com.adobe.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;

public class GAPlugin extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callback) {
		GoogleAnalytics ga = GoogleAnalytics.getInstance(cordova.getActivity());
		Tracker tracker = ga.getDefaultTracker(); 

		if (action.equals("initGA")) {
			try {
				tracker = ga.getTracker(args.getString(0));				
				GAServiceManager.getInstance().setLocalDispatchPeriod(args.getInt(1));
				ga.setDefaultTracker(tracker);
				callback.success("initGA - id = " + args.getString(0) + "; interval = " + args.getInt(1) + " seconds");
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("exitGA")) {
			try {
				GAServiceManager.getInstance().dispatchLocalHits();
				callback.success("exitGA");
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("trackEvent")) {
			try {
				tracker.send(MapBuilder
					      .createEvent(args.getString(0), 
                                       args.getString(1), 
                                       args.getString(2), 
                                       args.isNull(3) ? null : args.getLong(3))
					      .build()
					    );							
				callback.success("trackEvent - category = " + args.getString(0) + "; action = " + args.getString(1) + "; label = " + args.getString(2) + "; value = " + args.getInt(3));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		} else if (action.equals("trackPage")) {
			try {
				tracker.set(Fields.SCREEN_NAME,args.getString(0));
				tracker.send(MapBuilder
						  .createAppView()
						  .build()
						);
				callback.success("trackPage - url = " + args.getString(0));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		}
        else if (action.equals("trackSocial")) {
            try {
                    tracker.send(MapBuilder
                        .createSocial(args.getString(0),
                                      args.getString(1),      
                                      args.getString(2)).build());
                    callback.success("trackSocial = " + 
                                     args.getString(0));
                    return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
        }
        else if (action.equals("trackTiming")) {
            try {
                    tracker.send(MapBuilder
                        .createTiming(args.getString(0),
                                      args.getInt(1),      
                                      args.isNull(2) ? null : args.getString(2),
                                      args.isNull(3) ? null : args.getString(3)).build());
                    callback.success("trackTiming = " + 
                                     args.getString(0));
                    return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
        }
        else if (action.equals("setVariable")) {
			try {				
				tracker.send(MapBuilder.createAppView().set(Fields.customDimension(args.getInt(0)), args.getString(1)).build());			
				callback.success("setVariable passed - index = " + args.getInt(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		}
		else if (action.equals("setDimension")) {
			try {
				tracker.send(MapBuilder.createAppView().set(Fields.customDimension(args.getInt(0)), args.getString(1)).build());
				callback.success("setDimension passed - index = " + args.getInt(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		}
		else if (action.equals("setMetric")) {
			try {
				tracker.set(Fields.customMetric(args.getInt(0)), args.getString(1));
				tracker.send(MapBuilder.createAppView().build());			
				callback.success("setVariable passed - index = " + args.getInt(2) + "; key = " + args.getString(0) + "; value = " + args.getString(1));
				return true;
			} catch (final Exception e) {
				callback.error(e.getMessage());
			}
		}
        else
		    return false;
	}
}
