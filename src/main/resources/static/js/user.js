let index = {
    init: function(){
        $("#btn-save").on("click", ()=>{ //use arrow function to bind 'this'
            this.save();
        });
        $("#btn-update").on("click", ()=>{ //use arrow function to bind 'this'
            this.update();
        });

        // $("#btn-login").on("click", ()=>{ //use arrow function to bind 'this'
        //     this.login();
        // });
    },

    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        //default = asynchronous
        //ajax 통신을 이용해 데이터를 json으로 변경하여 insert 요청
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해준다.
        $.ajax({
            //request registration
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data), //http body data
            contentType: "application/json; charset=utf-8",
            dataType: "json" // default data type of response is string, but it converts into javascript object
        }).done(function(res){
            if (res.status === 500){
                alert("Registration Failed")
            }else{
                alert("Registration Success");
                location.href = "/";
            }
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); //ajax통신을 통해 데이터를 json으로 변경하여 insert 요청
    },

    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data), //http body data
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(res){
            alert("User Information is updated");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); //ajax통신을 통해 데이터를 json으로 변경하여 insert 요청
    },

    // login: function () {
    //     let data = {
    //         username: $("#username").val(),
    //         password: $("#password").val(),
    //     };
    //
    //     $.ajax({
    //         type: "POST",
    //         url: "/api/user/login",
    //         data: JSON.stringify(data), //http body data
    //         contentType: "application/json; charset=utf-8",
    //         dataType: "json" //response default data type is string, but it converts into javascript object
    //     }).done(function(res){
    //         alert("Login Success");
    //         location.href = "/";
    //     }).fail(function(error){
    //         alert(JSON.stringify(error));
    //     }); //ajax통신을 통해 데이터를 json으로 변경하여 insert 요청
    // }
};


index.init();
/*
 *ajax를 사용하는 이유
 *
 * 1. 요청에 대한 응답을 html이 아닌 Data(json)를 받기 위하여
 * 2. asynchronous 를 위해서
 * 앱과 브라우저에서 각각 응답에 대한 요청을 데이터(앱에서는 자바로 이미 구현되어 있다)와 html을 해주게 되는데 이렇게 되면 2개의 다른 서버가 필요하다.
 * 하나의 서버로 데이터를 리턴해주는 방식으로 구현을 하면 되는데 여기서 브라우저는 정상적인 데이터를 받은 후 다시 request를 통해 html을 응답 받는다.

 */
