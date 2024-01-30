<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import=" org.slf4j.Logger, org.slf4j.LoggerFactory, capstone.fe.spring.model.Destinazione, java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Destinazione</title>

<%@ include file="/WEB-INF/views/fragments/head.jspf" %>

<link rel="stylesheet" href="<c:url value="/resources/assets/css/dest.css" />"/>
<link rel="stylesheet" href="<c:url value="/resources/assets/css/prenotazione.css" />"/>
<link rel="stylesheet" href="<c:url value="/webjars/mdbootstrap/4.20.0/css/mdb.min.css" />"/>
<link rel="stylesheet" href="<c:url value="/resources/assets/css/catalogo-alloggi.css" />"/>
<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/webjars/mdbootstrap/4.20.0/js/mdb.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/dest.js" />"></script>
<script type="module" src="<c:url value="/resources/assets/js/catalogo-alloggi.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>
<script type="module" src="<c:url value="/resources/assets/js/prenotazione.js" />"></script>
</head>
<body>

<%! Logger logger = LoggerFactory.getLogger(this.getClass()); %>
<% logger.info(""+ request.getAttribute("jsonH")); %>
<script>
var baseUrl = '${baseUrl}';
var hotels = '${jsonH}';
var appartamenti = '${jsonA}';
var count = '${count}';
var nc = '${nc}';
var metaMap = new Map();
<% Long[] array = ((Destinazione)request.getAttribute("dest")).getCityIds();
	for(int i = 0; i< array.length; i++){%>
		metaMap.set(<%=array[i]%>,'<%=((List<String>)request.getAttribute("cities")).get(i)%>');
<%}%>

</script>
<section class="container-fluid mx-0 p-0" ng-app="metaApp" >

<div id="main" class="d-flex justify-content-center align-items-center flex-column w-100" ng-controller="metaCtrl" ng-init="initMeta()">
<%@ include file="/WEB-INF/views/fragments/warning.jspf" %>
 <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
	
	 <section class="mt-4 w-100 pb-0">
	  <%@ include file="/WEB-INF/views/fragments/hero.jspf" %>
	  </section>
	  
	  
<div class="container-fluid">

  <div class="row">
    <div class="col-2"></div>
    <div class="col-8">
      <c:if  test="${dest != null}">
      <div class="card w-100 shadow-none mb-5 mt-3">
        <div class="card-header section-title">
          <h2>${dest.nome}</h2>
        </div>
        <img class="card-img-top w-100 img-fluid z-1 w-100 h-100" src="${img}" alt="Card image cap" >
        <div class="card-body">
          <div class="my-2"><h4 class="card-text text-center">${dest.descrizione}</h4></div>
          <div class="my-2"><h4 class="card-text text-center">${dest.contenutoPrincipale}</h4></div>
          <div class="my-2"><h4 class="card-text text-center">${dest.contenutoSecondario}</h4></div>

          <div>

        </div>
        </div>
        </div>
      </c:if>
    </div>
    <div class="col-12 d-flex align-items-center justify-content-center"><h2>Città rilevanti</h2></div>
    <div class="col-3"></div>
    <div class="col-6 d-flex flex-column">
      <c:forEach items="${dest.cityIds}" var="cityId" varStatus="loop">
        <a href="<%= request.getContextPath() + "/city/" %>${cityId}" class="d-flex align-items-center my-3" > <span class="bg rounded-circle d-flex align-items-center justify-content-center"><i class="bi bi-geo-alt"></i></span ><span class="mx-3">${cities.get(loop.index)}</span></a>
      </c:forEach>
    </div>
    <div class="col-3"></div>

    <div class="col-12 section-title w-100 align-items-center justify-content-center">
      <h2></h2>
    </div>
    <div class="col-2"></div>
    <div class="col-12" ng-if="check()">
       <%@ include file="/WEB-INF/views/fragments/prenotazione.jspf" %>
    </div>

    <div class="col-12">
    <%@ include file="/WEB-INF/views/fragments/catalogo-alloggi.jspf" %>
    <!-- 
      <app-ctatalogo-alloggi [metaIds]="dest?.cityIds" (emit)="setAlloggio($event)"></app-ctatalogo-alloggi>
       -->
    </div>
  </div>

</div>
	  

	  
	  
	  
	   <%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
</div>

</section>
</body>
</html>