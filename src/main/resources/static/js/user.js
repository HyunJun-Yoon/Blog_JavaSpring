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
            dataType: "json" //response default data type is string, but it converts into javascript object
        }).done(function(res){
            alert("Registration Success");
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
 * 1. 요청에 대한 응답을 html이 아닌 Data(json)를 받기 위하여
 * 2. asynchronous 를 위해서

 */
