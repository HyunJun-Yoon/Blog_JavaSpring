let index = {
    init: function(){
        // $("#btn-login").on("click", ()=>{ //use arrow function to bind 'this'
        //     this.login();
        // });
        $("#btn-save").on("click", ()=>{
            this.save();
        });

        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{
            this.update();
        });

        $("#btn-comment-save").on("click", ()=>{
            this.commentSave();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

         $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res){
            alert("Your content is saved");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deleteById: function () {
        let id = $("#id").text();
        $.ajax({
            type: "DELETE",
            url: "/api/board/" +id,
            dataType: "json"
        }).done(function(res){
            alert("Deleted");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let id = $("#id").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            type: "PUT",
            url: "/api/board/"+id,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res){
            alert("Your content is saved");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    commentSave: function () {
        let data = {
            userId: $("#userId").val(),
            boardId: $("#boardId").val(),
            content: $("#comment-content").val(),
        };
        // let boardId = $("#boardId").val();

        $.ajax({
            type: "POST",
            url: `/api/board/${data.boardId}/comment`, //`/api/board/${boardId}/comment`
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res){
            alert("Your comment is saved");
            location.href = `/board/${data.boardId}`; //`/board/${boardId}`
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    commentDelete: function (boardId, commentId) {
        $.ajax({
            type: "DELETE",
            url: `/api/board/${boardId}/comment/${commentId}`,
            dataType: "json"
        }).done(function(res){
            alert("Your comment is deleted");
            location.href = `/board/${boardId}`; //`/board/${boardId}`
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
};


index.init();
