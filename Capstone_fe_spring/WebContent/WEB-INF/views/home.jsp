
 
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<%@ page import=" org.slf4j.Logger, org.slf4j.LoggerFactory"%>

<html>
<head>
<title>Home</title>

 <%@ include file="/WEB-INF/views/fragments/head.jspf" %>

<link rel="stylesheet" href="<c:url value="/resources/assets/css/home.css" />"/>
<link rel="stylesheet" href="<c:url value="/webjars/mdbootstrap/4.20.0/css/mdb.min.css" />"/>
<script src="<c:url value="/webjars/bootstrap/5.3.2/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/webjars/mdbootstrap/4.20.0/js/mdb.min.js" />"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.1/angular.min.js"></script>
<script type="module" src="<c:url value="/resources/assets/js/home.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/assets/js/warning.js" />"></script>
</head>
<body>

<script>

var baseUrl = '${baseUrl}';
var array = '${jsonImgs}';
var count = '${count}';
var mete = '${json}';
//console.table('${jsonP}');
var map = '${jsonP}';
var token = '${token}';

</script>
<section class="container-fluid mx-0 p-0" ng-app="homeApp" >

<div id="main" class="d-flex justify-content-center align-items-center flex-column w-100" ng-controller="homeCtrl" ng-init="initHome()">
<%@ include file="/WEB-INF/views/fragments/warning.jspf" %>
	 <%@ include file="/WEB-INF/views/fragments/header.jspf" %>
	
	 <section class="mt-4 w-100 pb-0">
	  <%@ include file="/WEB-INF/views/fragments/hero.jspf" %>
	  </section>
	 <section class="py-0">
	 <%! Logger logger = LoggerFactory.getLogger(this.getClass()); %>
	 
	
	
	 <div class="container-fluid w-100 h-100 p-0" id="c">

  <div class="row px-0 py-0 ts" id="r">
  <c:choose>
	 <c:when test="${data.size() > 0}">
	 
	 <c:forEach items="${data}" var="meta" >
	 
	  <div class="col-12 ts z-1">
      <div class="card h-100 w-100 ts ">
        <div class="card-header d-flex flex-column w-50 mx-5 my-5" *ngIf="true">
          <div class="d-flex align-items-center justify-content-between py-1">
              <h2 class="card-title"><c:out value = "${meta.nome}"/></h2>
              <button id="mSel"type="button" class="btn btn-trasparent p-1" (click)="selectMeta(meta)">

                <span class="d-flex align-items-center justify-content-center">
                  <i class="fa-solid fa-arrow-right fa-2xl h-100 w-100"></i>
                  <!--
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#444444" class="bi bi-arrow-right-circle" viewBox="0 0 16 16">
                  <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H4.5z"/>
                </svg>
                -->
              </span>
              </button>
          </div>
        </div>
<!--
        <img class="card-img-top w-100 img-fluid z-1" [src]="getMetaImgUrl(meta)" alt="Card image cap" >
-->
<div class="w-100 ts middle"></div>
        <div class="card-body z-3 d-flex flex-column align-items-center w-100 justify-content-center">
          <p class="card-text w-50"><c:out value = "${meta.descrizione}"/></p>
        </div>
        <c:if test="${user != null}">
        <div class="card-footer z-3 d-flex align-items-center flex-column w-100 justify-content-center" >

          <button class="btn btn-otlline-success p-1 text-white px-4 py-2 d-flex align-items-center" ng-click="mostraPacchetti(${meta.id})">
            <span class="mx-2">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#209dd8" class="bi bi-backpack4" viewBox="0 0 16 16">
                <path d="M4 9.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-.5.5h-7a.5.5 0 0 1-.5-.5v-4Zm1 .5v3h6v-3h-1v.5a.5.5 0 0 1-1 0V10H5Z"/>
                <path d="M8 0a2 2 0 0 0-2 2H3.5a2 2 0 0 0-2 2v1c0 .52.198.993.523 1.349A.5.5 0 0 0 2 6.5V14a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V6.5a.5.5 0 0 0-.023-.151c.325-.356.523-.83.523-1.349V4a2 2 0 0 0-2-2H10a2 2 0 0 0-2-2Zm0 1a1 1 0 0 0-1 1h2a1 1 0 0 0-1-1ZM3 14V6.937c.16.041.327.063.5.063h4v.5a.5.5 0 0 0 1 0V7h4c.173 0 .34-.022.5-.063V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1Zm9.5-11a1 1 0 0 1 1 1v1a1 1 0 0 1-1 1h-9a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1h9Z"/>
              </svg>
            </span ><span class="mx-2">Pacchetto vacanza</span></button>
<section ng-if="check.get(${meta.id})" class="w-100">
 <%@ include file="/WEB-INF/views/fragments/pacchetti.jspf" %>
 </section>
<!--  
            <app-pacchetti [meta] = "meta" (emitter)="checks.set(meta.id,$event)" *ngIf="checks.get(meta.id)" class="w-100"></app-pacchetti>
            -->
        </div>
        </c:if>
      </div>
    </div>
    </c:forEach>
    </c:when>
     <c:otherwise>
        <h1>Lista vuota</h1>
    </c:otherwise>
    </c:choose>
    </div>
    </div>
	 </section>
	  <%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
</div>
</section>
</body>
</html>