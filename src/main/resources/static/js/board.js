let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{ //use arrow function to bind 'this'
            this.save();
        });
        // $("#btn-login").on("click", ()=>{ //use arrow function to bind 'this'
        //     this.login();
        // });
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

};


index.init();
