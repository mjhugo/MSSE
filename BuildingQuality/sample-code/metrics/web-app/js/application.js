if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

function initPeriodicalUpdater(url, completionUrl) {
    var periodicalUpdaterHandler = $.PeriodicalUpdater(url, {
        method: 'get',          // method; get or post
        data: '',                   // array of values to be passed to the page - e.g. {name: "John", greeting: "hello"}
        minTimeout: 1000,       // starting value for the timeout in milliseconds
        maxTimeout: 8000,       // maximum length of time between requests
        multiplier: 2,          // if set to 2, timerInterval will double each time the response hasn't changed (up to maxTimeout)
        type: 'json',           // response type - text, xml, json, etc.  See $.ajax config options
        maxCalls: 0,            // maximum number of calls. 0 = no limit.
        autoStop: 0             // automatically stop requests after this many returns of the same data. 0 = disabled.
    }, function(data) {
        $('.importPercentComplete').html(data.text);
        $('#progressBar').progressbar("value", parseInt(data.value));
        if (100 == parseInt(data.value)) {
            periodicalUpdaterHandler.stop();
            $('#progressBar').siblings('.close').click();
            if (completionUrl) {
                window.location = completionUrl;
            }
        }
    });

}
