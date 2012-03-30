/*
	This is the Geb configuration file.
	
	See: http://www.gebish.org/manual/current/configuration.html
*/

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.ie.InternetExplorerDriver

// Use htmlunit as the default
// See: http://code.google.com/p/selenium/wiki/HtmlUnitDriver
driver = { 
	def driver = new HtmlUnitDriver()
	driver.javascriptEnabled = true
	driver
}

environments {
	
	// run as “grails -Dgeb.env=chrome test-app”
	// See: http://code.google.com/p/selenium/wiki/ChromeDriver
    // to run with chrome, download server per instructions on wiki link above
    // then run with webdriver.chrome.driver set
    // e.g. -Dwebdriver.chrome.driver="/Users/mjhugo/Downloads/chromedriver"
	chrome {
		driver = { new ChromeDriver() }
	}
	
	// run as “grails -Dgeb.env=firefox test-app”
	// See: http://code.google.com/p/selenium/wiki/FirefoxDriver
	firefox {
		driver = { new FirefoxDriver() }
	}

	// run as “grails -Dgeb.env=ie test-app”
	// See: http://code.google.com/p/selenium/wiki/InternetExplorerDriver
	ie {
		driver = { new InternetExplorerDriver() }
	}

}