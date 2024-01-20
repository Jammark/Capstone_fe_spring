<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import=" org.slf4j.Logger, org.slf4j.LoggerFactory"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mete</title>

 <%@ include file="/WEB-INF/views/fragments/head.jspf" %>

<link rel="stylesheet" href="<c:url value="/resources/assets/css/mete.css" />"/>
<link rel="stylesheet" href="<c:url value="/webjars/mdbootstrap/4.20.0/css/mdb.min.css" />"/>

<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/webjars/mdbootstrap/4.20.0/js/mdb.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/mete.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>
</head>
<body>
<section class="container-fluid mx-0 p-0" ng-app="meteApp" >

<div id="main" class="d-flex justify-content-center align-items-center flex-column w-100" ng-controller="meteCtrl" ng-init="initMete()">
 <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
	
	 <section class="mt-4 w-100 pb-0">
	  <%@ include file="/WEB-INF/views/fragments/hero.jspf" %>
	  </section>
	  
	  <div id="main2" class="container-fluid w-100 h-100 p-0">

  <div class="row px-0 py-0 ts position-relative">



    <div class="col-12">
    <!-- Tabs navs -->
<ul class="nav nav-tabs md-tabs" id="myTabEx" role="tablist">
  <li class="nav-item">
    <a class="nav-link active show" id="città-tab-ex" data-toggle="tab" href="#home-ex" role="tab" aria-controls="home-ex"
      aria-selected="true">Città</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="dest-tab-ex" data-toggle="tab" href="#profile-ex" role="tab" aria-controls="profile-ex"
      aria-selected="false">Destinazioni</a>
  </li>
 
</ul>
<!--  
<div class="tab-content pt-5" id="myTabContentEx">
  <div class="tab-pane fade active show" id="home-ex" role="tabpanel" aria-labelledby="città-tab-ex">

  </div>
  <div class="tab-pane fade" id="profile-ex" role="tabpanel" aria-labelledby="dest-tab-ex">
    
  </div>
 
</div>
-->
<!-- Tabs content -->
  
<!-- Tabs content -->
    <!--

<ul class="nav nav-tabs mb-3" id="ex1" role="tablist">
  <li class="nav-item" role="presentation">
    <a
      data-mdb-tab-init
      class="nav-link active"
      id="ex1-tab-1"
      href="#ex1-tabs-1"
      role="tab"
      aria-controls="ex1-tabs-1"
      aria-selected="true"
      >Città</a
    >
  </li>
  <li class="nav-item" role="presentation">
    <a
      data-mdb-tab-init
      class="nav-link"
      id="ex1-tab-2"
      href="#ex1-tabs-2"
      role="tab"
      aria-controls="ex1-tabs-2"
      aria-selected="false"
      >Destinazioni</a
    >
  </li>

</ul>

  -->
</div>
<!-- Tabs navs -->
<!-- 
      <mdb-tabs>
        <mdb-tab title="Città">
         -->
   <div class="col-12 h-100 tab-content" id="myTabContentEx">
  <div class="container-fluid w-100 h-100 p-2 tab-pane fade active show" id="home-ex" role="tabpanel" aria-labelledby="città-tab-ex">

    <c:if test="${città != null && città.size() > 0}">
      <div class="row px-0 py-0 ts gy-3">
      <c:forEach items="${città}" var="city" varStatus="loop">

        <div class="col-lg-3 col-md-4" >
          <div class="card position-relative" (click)="selezionaCity(city)">
            <img class="card-img-top w-100 img-fluid z-1 w-100 h-100" src="${imgC.get(loop.index).getImg()} " alt="Card image cap" >
            <div class="card-body flex-column position-absolute z-1 align-items-start p-0">
                <h2 class="card-title p-2 rounded bg-white txt-c"><c:out value = "${city.nome}"/></h2>
                <h2 class="card-title p-2 rounded bg-white"><span><c:out value = "${city.numHotels}"/></span><span class="txt-c"> Hotel</span></h2>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>
      </c:if>
  </div>
 
      <!--     </mdb-tab>
        <mdb-tab title="Destinazioni">
       -->
        <div class="col-12 h-100" >
  <div class="container-fluid w-100 h-100 p-2 tab-pane fade" id="profile-ex" role="tabpanel" aria-labelledby="dest-tab-ex">
      <c:if test="${destinazioni != null && destinazioni.size() > 0}"
        >
        <div class="row px-0 py-0 ts gy-3">
      <c:forEach items="${destinazioni}" var="dest" varStatus="loop">
        <div class="col-lg-3 col-md-4" >
          <div class="card position-relative " (click)="selezionaDest(dest)">
            <img class="card-img-top w-100 img-fluid z-1 w-100 h-100" src="${imgD.get(loop.index).getImg()} " alt="Card image cap" >
            <div class="card-body flex-column position-absolute z-1 align-items-start p-0">
                <h2 class="card-title p-2 rounded bg-white txt-c"><c:out value = "${dest.nome}"/></h2>
            </div>
          </div>
        </div>

     
   
      </c:forEach>
      
       </div>
       </c:if>

    </div>
    </div>
    <!--  
  </mdb-tab>
      </mdb-tabs>
      
      -->
 

    </div>
          </div>


	  
	  
	  
	    <%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
</div>

</section>
</body>
</html>