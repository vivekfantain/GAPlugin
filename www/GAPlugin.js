(function(){
    var cordovaRef = window.PhoneGap || window.cordova || window.Cordova;

    function GAPlugin() { }

    // initialize google analytics with an account ID and the min number of seconds between posting
    //
    // id = the GA account ID of the form 'UA-00000000-0'
    // period = the minimum interval for transmitting tracking events if any exist in the queue
    GAPlugin.prototype.init = function(success, fail, id, period) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'initGA', [id, period]);
    };

    // log an event
    //
    // category = The event category. This parameter is required to be non-empty.
    // eventAction = The event action. This parameter is required to be non-empty.
    // eventLabel = The event label. This parameter may be a blank string to indicate no label.
    // eventValue = The event value. This parameter may be -1 to indicate no value.
    GAPlugin.prototype.trackEvent = function(success, fail, category, eventAction, eventLabel, eventValue) {

        return cordovaRef.exec(success, fail, 'GAPlugin', 'trackEvent',
                [category, eventAction, eventLabel, eventValue]);

    };

    // Log a social event
    // socialnetwork - the network which you will be posting
    // socialAction - what action is being taken on the social network (tweek, post, like etc., )
    //
    //
    GAPlugin.prototype.trackSocial = function(success, fail, socialnetwork, socialAction, socialURL) {
    
        return cordovaRef.exec(success, fail, 'GAPlugin', 'trackSocial',
                [socialnetwork, socialAction, socialURL]); };

    // Log a timing event
    //
    //category - Timing category
    //timing - the time in milliseconds...
    //name - optional : name of the timing event  
    //label - optional : label of the timing event  
    GAPlugin.prototype.trackTiming = function(success, fail, category, timing, name, label) {
    
        return cordovaRef.exec(success, fail, 'GAPlugin', 
                                              'trackTiming', 
                                              [category, timing, 
                                               name, label]);
    };

    // log a page view
    //
    // pageURL = the URL of the page view
    GAPlugin.prototype.trackPage = function(success, fail, pageURL) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'trackPage', [pageURL]);
    };

    // Set a custom variable. The variable set is included with
    // the next event only. If there is an existing custom variable at the specified
    // index, it will be overwritten by this one.
    //
    // value = the value of the variable you are logging
    // index = the numerical index of the dimension to which this variable will be assigned (1 - 20)
    //  Standard accounts support up to 20 custom dimensions.
    GAPlugin.prototype.setVariable = function(success, fail, index, value) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'setVariable', [index, value]);
    };

    GAPlugin.prototype.setDimension = function(success, fail, dimname, dimvalue)
    {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'setDimension', [dimname, dimvalue]);
    }
    
    GAPlugin.prototype.setMetric = function(success, fail, mname, mvalue)
    {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'setMetric', [mname, mvalue]);
    }

    GAPlugin.prototype.exit = function(success, fail) {
        return cordovaRef.exec(success, fail, 'GAPlugin', 'exitGA', []);
    };
 
    if (cordovaRef && cordovaRef.addConstructor) {
        cordovaRef.addConstructor(init);
    }
    else {
        init();
    }

    function init () {
        if(!window.plugins) {
            window.plugins = {};
        }
        if(!window.plugins.gaPlugin) {
            window.plugins.gaPlugin = new GAPlugin();
        }
    }

    if (typeof module != 'undefined' && module.exports) {
        module.exports = new GAPlugin();
    }
})(); /* End of Temporary Scope. */
