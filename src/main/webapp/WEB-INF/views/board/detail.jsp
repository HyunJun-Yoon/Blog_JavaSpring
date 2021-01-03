<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
<div class="container">
        <button class="btn btn-secondary" onclick="history.back()">Go Back</button>

        <c:if test="${board.user.id == principal.user.id}">
         <a href="/board/${board.id}/updateForm" class="btn btn-warning">Edit</a>
         <button id="btn-delete" class="btn btn-danger">Delete</button>
        </c:if>
        <br/><br/>
        <div>
            NUMBER : <span id="id"><i>${board.id} </i></span>
            AUTHOR : <span><i>${board.user.username} </i></span>
        </div>
        <br/>
        <div>
            <h3>${board.title}</h3>
        </div>
        <hr />
        <div>
            <div>${board.content}</div>
        </div>
        <hr />

        <div class="card">
            <form>
                <input type="hidden" id="userId" value="${principal.user.id}"/>
                <input type="hidden" id="boardId" value="${board.id}"/>
                <div class="card-body">
                    <textarea  id="comment-content" class="form-control" rows="1"></textarea>
                </div>
                <div class="card-footer">
                    <button type="button" id="btn-comment-save" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
        <br/>

        <div class="card">
            <div class="card-header">Comments</div>
            <ul id="comment-box" class="list-group">
                <c:forEach var="comment" items="${board.comments}">
                    <li id="comment-${comment.id}" class="list-group-item d-flex justify-content-between">
                        <div>${comment.content}</div>
                        <div class="d-flex">
                            <div class="font-italic">Author: ${comment.user.username}&nbsp;</div>
                            <button onclick="index.commentDelete(${board.id}, ${comment.id})" class="badge">Delete</button>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>

        </div>


</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp" %>

