let index = {
    init: function(){
        // $("#btn-login").on("click", ()=>{ //use arrow function to bind 'this'
        //     this.login();
        // });
        $("#btn-save").on("click", ()=>{ //use arrow function to bind 'this'
            this.save();
        });

        $("#btn-delete").on("click", ()=>{ //use arrow function to bind 'this'
            this.deleteById();
        });

        $("#btn-update").on("click", ()=>{ //use arrow function to bind 'this'
            this.update();
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
};


index.init();
