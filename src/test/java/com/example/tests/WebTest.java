package com.example.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    //利用kalaton插件导出的url是   baseUrl = "https://www.katalon.com/";
    //before部分应该最多修改此处的这个baseurl，，可以便于以后的代码中的使用
    baseUrl = "https://psych.liebes.top/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
	  //主要对这里的代码进行修改
	  //input文件中有学号和相应的github网址，
	  
	  try {
		  //测试是否进入了try语句段
		  System.out.println("go in try ");
          //创建输出信息的txt文件
          File writename = new File("D:\\专业核心\\软件测试\\上机2\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
          writename.createNewFile(); // 创建新文件
          BufferedWriter out = new BufferedWriter(new FileWriter(writename));

          out.write("   学号                  原URL                                excel表中URL\r\n");
          out.flush(); // 把缓存区内容压入文件

          // 指定excel的路径
          File src = new File("D:\\input.xlsx");
          // 加载文件
          FileInputStream fis = new FileInputStream(src);
          // 加载workbook，将文件读取为Excel的workbook类XSSFWorkbook的实例wb
          @SuppressWarnings("resource")
          XSSFWorkbook wb = new XSSFWorkbook(fis);
          // 从workbook中提取初第一个sheet，名sh1
          XSSFSheet sh1 = wb.getSheetAt(0);
          //在sh1的所有行中循环，
          for (int i = 0; i < sh1.getPhysicalNumberOfRows(); i++) {
        	  //测试循环的个数
        	  System.out.println("count = "+((Integer)i).toString());
        	  //在浏览器中输入本作业中需要输入数据的网址
        	  driver.get("https://psych.liebes.top/st");
        	  //清空用户名位置的原有信息
              driver.findElement(By.id("username")).clear();
              //将本次循环中，循环到的这一行Excel中的第一个元素，也就是第i行的第0个元素的值，放置到用户名（username）位置中
              driver.findElement(By.id("username")).sendKeys(
                      sh1.getRow(i).getCell(0).getStringCellValue());
             //清空密码位置的原有信息
              driver.findElement(By.id("password")).clear();
            //将本次循环中，循环到的这一行Excel中的第一个元素，也就是第i行的第0个元素，去除他的前四位，也就是保留后六位，
              //放置到用户名（username）位置中
              driver.findElement(By.id("password")).sendKeys(
                      sh1.getRow(i).getCell(0).getStringCellValue()
                              .substring(4));
              //然后点击submitButton按键
              driver.findElement(By.id("submitButton")).click();
              //然后对跳转的网页的网址和Excel中的信息进行对比
              if (sh1.getRow(i)
                      .getCell(1)
                      .getStringCellValue()
                      .trim()
                      .equals(driver
                              .findElement(By.cssSelector("p.login-box-msg"))
                              .getText().trim())) {
            	  //如果匹配则输出success信息
                  System.out.println("Success-"
                          + sh1.getRow(i).getCell(0).getStringCellValue());
              } else {
            	  //如果不匹配则输出failed信息
                  System.out.println("Failed-"
                          + sh1.getRow(i).getCell(0).getStringCellValue());
                  //并将信息写入txt文件中
                  out.write(sh1.getRow(i).getCell(0).getStringCellValue()
                          + "     "
                          + String.format(
                                  "%-40s",
                                  driver.findElement(
                                          By.cssSelector("p.login-box-msg"))
                                          .getText())
                          + String.format("%-40s", sh1.getRow(i).getCell(1)
                                  .getStringCellValue()) + "\r\n"); // \r\n即为换行
                  out.flush(); // 把缓存区内容压入文件
              }

          }
          out.close(); // 最后记得关闭文件
      } catch (Exception e) {
    	  System.out.println("try error");
          System.out.println(e.getMessage());
      }
	 
  }

  
  //后面的部分不需要修改
  
  @After
  public void tearDown() throws Exception {
	  //driver.quit();遇到问题，所以此处更改为driver.close();
    driver.close();
	  //driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
