function login() {
    console.log("JavaScript login 호출");

    // 유저가 입력한 값
    const inputId = document.getElementById("loginId").value;
    const inputPwd = document.getElementById("loginPassword").value;

    $.ajax({
       type: "POST",
       url: "/login",
       contentType: "application/json",
       data: JSON.stringify({
           userId : inputId,
           userPassword : inputPwd,
       }),
       success: function success(res) {
           if(res === "로그인 성공") {
               swal("로그인 성공", "", "success");
               window.location.href = "/form";
           } else {
               swal("로그인 실패", "계정, 암호를 확인해주세요.", "error");
           }
       },
       error: function error(err) {

       }
    });
}