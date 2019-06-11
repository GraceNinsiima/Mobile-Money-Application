package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Mobile Money</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Form for registering Kiosks</h1>\n");
      out.write("        <form action = \"insert.jsp\" method=\"post\">\n");
      out.write("            <table>\n");
      out.write("                <tr><td>Kiosk Name:</td>\n");
      out.write("                    <td><Input type=\"text\" name=\"kioskName\"/></td></tr>\n");
      out.write("                <tr><td>Location:</td>\n");
      out.write("                    <td><Input type=\"text\" name =\"location\"/></td></tr>\n");
      out.write("                <tr><td>User Name:</td>\n");
      out.write("                    <td><input type=\"text\" name=\"username\"/></td></tr>\n");
      out.write("                <tr><td>Mtn Sim Card Number:</td>\n");
      out.write("                    <td><Input type=\"tel\" name =\"mtnSimNo\"/></td></tr>\n");
      out.write("                <tr><td>Mtn Float:</td>\n");
      out.write("                    <td><input type = \"text\" name = \"mtnFloat\"/></td></tr>\n");
      out.write("                <tr><td>Airtel Sim Card Number:</td>\n");
      out.write("                    <td><Input type=\"tel\" name =\"airtelSimNo\"/></td></tr>\n");
      out.write("                <tr><td>Airtel Float:</td>\n");
      out.write("                    <td><input type = \"text\" name = \"airtelFloat\"/></td></tr>\n");
      out.write("                <tr><td>Cash:</td>\n");
      out.write("                    <td><Input type=\"text\" name =\"cash\"/></td></tr>\n");
      out.write("                <tr><td></td>\n");
      out.write("                    <td><Input type=\"submit\" name =\"submit\" value = \"Submit\"/>\n");
      out.write("                    <Input type=\"reset\" name =\"cancel\" value =\"Cancel\"/></td></tr>\n");
      out.write("            </table>\n");
      out.write("        </form>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
