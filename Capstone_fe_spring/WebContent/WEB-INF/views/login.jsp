 
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<head>
<title>Login</title>


<%@ include file="/WEB-INF/views/fragments/head.jspf" %>
<spring:url value="/resources/css/login.css" var="crunchifyCSS" />
<link rel="stylesheet" href="<c:url value="/resources/assets/css/login.css" />"/>
<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/login.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>


</head>
<body>
<script>
	console.log('${baseUrl}');
	var baseUrl = '${baseUrl}';
	var loggedMsg;
</script>
<%
if(request.getParameter("notlogged") != null){
	Boolean logged = Boolean.valueOf(request.getParameter("notlogged"));
	if(logged){%>
	
	<script type="text/javascript">
	console.log('not logged in');
	 loggedMsg = 'Utente non loggato.';
	
	</script>
	
	<% }
	
	
}

%>

<jsp:useBean id="obj" class="capstone.fe.spring.model.LoginRequest"/>  
  
<jsp:setProperty property="*" name="obj"/>  
<section class="container" ng-app="loginApp">
<div id="main" class="d-flex justify-content-center align-items-center" ng-controller="loginCtrl" ng-init="initLogin('<%=request.getContextPath()%>')">
<%@ include file="/WEB-INF/views/fragments/warning.jspf" %>
  <div>
    <form>
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="username" class="form-control" required >
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" required >
        </div>
        <button id="btn" type="submit" class="btn btn-primary mt-3" disabled="" ng-click="login()"><i class="fa-solid fa-right-to-bracket me-2"></i>Accedi</button>
    </form>
    <div class="my-2 w-100 d-flex justify-content-center">
      <a  href="register" class="active">Registrati</a>
    </div>
  </div>

              <!--Modal: modalPush-->
              <div class="modal fade" id="modalSuccess" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
              aria-hidden="true">
              <div class="modal-dialog modal-notify modal-info" role="document">
                <!--Content-->
                <div class="modal-content text-center">
                  <!--Header-->
                  <div class="modal-header d-flex justify-content-center">
                    <p class="heading">Prego!</p>
                  </div>

                  <!--Body-->
                  <div class="modal-body">

                    <i class="fas fa-solid fa-thumbs-up fa-4x animated rotateIn mb-4"></i>

                    <p>Autenticazione avvenuta con successo.</p>

                  </div>

                  <!--Footer-->
                  <div class="modal-footer flex-center">

                    <a type="button" class="btn btn-outline-info waves-effect"  data-bs-dismiss="modal" ng-click ="navHome()">OK</a>
                  </div>
                </div>
                <!--/.Content-->
              </div>
              </div>
              <!--Modal: modalPush-->

</div>
</section>

</body>
</html>