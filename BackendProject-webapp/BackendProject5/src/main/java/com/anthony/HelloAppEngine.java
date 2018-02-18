package com.anthony;



import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@WebServlet(name = "HelloAppEngine", value = "/hello")
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Translate translate = TranslateOptions.getDefaultInstance().getService();

    // The text to translate
    String text = request.getParameter("text");

    // Translates some text into Russian
    Translation translation =
            translate.translate(
                    text,
                    TranslateOption.sourceLanguage("en"),
                    TranslateOption.targetLanguage("es"));


    System.out.printf("Text: %s%n", text);
    System.out.printf("Translation: %s%n", translation.getTranslatedText());
    response.addHeader("Access-Control-Allow-Origin",  "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");

    response.addHeader("Access-Control-Max-Age",       "1800");
    response.setLocale(new Locale("es", "ES"));
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print(translation.getTranslatedText());
    //doResponse(request, response, "response");
  }

  public static String getInfo() {
    return "Version: " + System.getProperty("java.version")
          + " OS: " + System.getProperty("os.name")
          + " User: " + System.getProperty("user.name");
  }
  public void doResponse(
          HttpServletRequest  req,
          HttpServletResponse rsp,
          Object              response)
          throws ServletException, IOException
  {
    // enable cors                         //
    rsp.addHeader("Access-Control-Allow-Origin",  "*");
    rsp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");

    rsp.addHeader("Access-Control-Max-Age",       "1800");

    // support all requested headers       //
    for (Enumeration<String> ctlReqHeaders =
         req.getHeaders("Access-control-request-headers");
         ctlReqHeaders.hasMoreElements();)
    {
      rsp.addHeader("Access-Control-Allow-Headers", ctlReqHeaders.nextElement());
    }
    if (response instanceof Throwable)
    {
      rsp.sendError(
              HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.toString());
    }
    else
    {
      rsp.getWriter().print(response);
    }

  }

}
