package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.support.ui.FluentWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

public class WebSiteUtility {

	WebDriver driver;
	
public WebDriver openBrowser(String bn)
	{
		if (bn.equalsIgnoreCase("chrome"))
		{
			driver= new ChromeDriver();
		}
		else if(bn.equalsIgnoreCase("edge"))
		{
			driver =new EdgeDriver();
		}
		else
		{
			
			RuntimeException e= new RuntimeException("Check browser name");
			throw(e);
		}
		return(driver);
	}

public FluentWait<WebDriver> defexpwait(WebDriver driver,int timeoutsec, int intervalsec)
	{
		FluentWait<WebDriver> wait =new FluentWait<WebDriver>(driver);
		wait.withTimeout(Duration.ofSeconds(timeoutsec));
		wait.pollingEvery(Duration.ofMillis(intervalsec));
		return(wait);
	}

public void launSite(WebDriver driver, String url)
{
	driver.get(url);
}

public void closeSite(WebDriver driver)
{
	driver.close();
}

public String capturePageScreenshotFile(WebDriver driver) throws Exception
{
	SimpleDateFormat sf= new SimpleDateFormat("dd-MMM-YYYY-hh-mm-ss");
	Date dt= new Date();
	String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
	File dest=new File(fn);//create a new file in HDD
	File src=((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
	FileHandler.copy(src,dest);
	return(dest.getAbsolutePath());
}

public String captureElementScreenshotFile(WebElement e) throws Exception
{
	SimpleDateFormat sf= new SimpleDateFormat("dd-MMM-YYYY-hh-mm-ss");
	Date dt= new Date();
	String fn=System.getProperty("user.dir")+"\\target\\"+sf.format(dt)+".png";
	File dest=new File(fn);//create a new file in HDD
	File src=e.getScreenshotAs(OutputType.FILE);
	FileHandler.copy(src,dest);
	return(dest.getAbsolutePath());
}

public String captureFullPageScreenshotFile(WebDriver driver) throws Exception
{
	SimpleDateFormat sf= new SimpleDateFormat("dd-MMM-YYYY-hh-mm-ss");
	Date dt= new Date();
	File dest=new File("target/"+sf.format(dt)+".png");
	AShot as =new AShot();																//create a new file in HDD
	ShootingStrategy shs=ShootingStrategies.viewportPasting(1000); //1  second delay
	Screenshot ss=as.shootingStrategy(shs).takeScreenshot(driver);
	ImageIO.write(ss.getImage(),"PNG",dest);
	return(dest.getAbsolutePath());
}
}
