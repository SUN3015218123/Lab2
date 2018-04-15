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
    //����kalaton���������url��   baseUrl = "https://www.katalon.com/";
    //before����Ӧ������޸Ĵ˴������baseurl�������Ա����Ժ�Ĵ����е�ʹ��
    baseUrl = "https://psych.liebes.top/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testUntitledTestCase() throws Exception {
	  //��Ҫ������Ĵ�������޸�
	  //input�ļ�����ѧ�ź���Ӧ��github��ַ��
	  
	  try {
		  //�����Ƿ������try����
		  System.out.println("go in try ");
          //���������Ϣ��txt�ļ�
          File writename = new File("D:\\רҵ����\\�������\\�ϻ�2\\output.txt"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
          writename.createNewFile(); // �������ļ�
          BufferedWriter out = new BufferedWriter(new FileWriter(writename));

          out.write("   ѧ��                  ԭURL                                excel����URL\r\n");
          out.flush(); // �ѻ���������ѹ���ļ�

          // ָ��excel��·��
          File src = new File("D:\\input.xlsx");
          // �����ļ�
          FileInputStream fis = new FileInputStream(src);
          // ����workbook�����ļ���ȡΪExcel��workbook��XSSFWorkbook��ʵ��wb
          @SuppressWarnings("resource")
          XSSFWorkbook wb = new XSSFWorkbook(fis);
          // ��workbook����ȡ����һ��sheet����sh1
          XSSFSheet sh1 = wb.getSheetAt(0);
          //��sh1����������ѭ����
          for (int i = 0; i < sh1.getPhysicalNumberOfRows(); i++) {
        	  //����ѭ���ĸ���
        	  System.out.println("count = "+((Integer)i).toString());
        	  //������������뱾��ҵ����Ҫ�������ݵ���ַ
        	  driver.get("https://psych.liebes.top/st");
        	  //����û���λ�õ�ԭ����Ϣ
              driver.findElement(By.id("username")).clear();
              //������ѭ���У�ѭ��������һ��Excel�еĵ�һ��Ԫ�أ�Ҳ���ǵ�i�еĵ�0��Ԫ�ص�ֵ�����õ��û�����username��λ����
              driver.findElement(By.id("username")).sendKeys(
                      sh1.getRow(i).getCell(0).getStringCellValue());
             //�������λ�õ�ԭ����Ϣ
              driver.findElement(By.id("password")).clear();
            //������ѭ���У�ѭ��������һ��Excel�еĵ�һ��Ԫ�أ�Ҳ���ǵ�i�еĵ�0��Ԫ�أ�ȥ������ǰ��λ��Ҳ���Ǳ�������λ��
              //���õ��û�����username��λ����
              driver.findElement(By.id("password")).sendKeys(
                      sh1.getRow(i).getCell(0).getStringCellValue()
                              .substring(4));
              //Ȼ����submitButton����
              driver.findElement(By.id("submitButton")).click();
              //Ȼ�����ת����ҳ����ַ��Excel�е���Ϣ���жԱ�
              if (sh1.getRow(i)
                      .getCell(1)
                      .getStringCellValue()
                      .trim()
                      .equals(driver
                              .findElement(By.cssSelector("p.login-box-msg"))
                              .getText().trim())) {
            	  //���ƥ�������success��Ϣ
                  System.out.println("Success-"
                          + sh1.getRow(i).getCell(0).getStringCellValue());
              } else {
            	  //�����ƥ�������failed��Ϣ
                  System.out.println("Failed-"
                          + sh1.getRow(i).getCell(0).getStringCellValue());
                  //������Ϣд��txt�ļ���
                  out.write(sh1.getRow(i).getCell(0).getStringCellValue()
                          + "     "
                          + String.format(
                                  "%-40s",
                                  driver.findElement(
                                          By.cssSelector("p.login-box-msg"))
                                          .getText())
                          + String.format("%-40s", sh1.getRow(i).getCell(1)
                                  .getStringCellValue()) + "\r\n"); // \r\n��Ϊ����
                  out.flush(); // �ѻ���������ѹ���ļ�
              }

          }
          out.close(); // ���ǵùر��ļ�
      } catch (Exception e) {
    	  System.out.println("try error");
          System.out.println(e.getMessage());
      }
	 
  }

  
  //����Ĳ��ֲ���Ҫ�޸�
  
  @After
  public void tearDown() throws Exception {
	  //driver.quit();�������⣬���Դ˴�����Ϊdriver.close();
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
