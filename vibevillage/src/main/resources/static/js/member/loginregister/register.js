// 계정 중복검사
function checkId() {
    const userId = document.getElementById("userId").value;

    if(userId === "") {
        swal("계정을 입력해주세요.", "", "error");
    } else {
        $.ajax({
            type: "GET",
            url: "/checkId",
            data: {
                userId : userId,
            },
            success: function success(res){
                if(res === "중복") {
                    swal("이미 사용중인 계정입니다.", "", "error")
                } else if (res === "가능") {
                    swal("사용 가능한 계정입니다.", "", "success")
                }
            },
            error: function error(err) {

            }
        });
    }
}

// 닉네임 중복검사
function checkNickName() {
    const nickName = document.getElementById("nickName").value;

    if(nickName === ""){
        swal("닉네임을 입력해주세요.", "", "error");
    } else {
        $.ajax({
           type: "GET",
           url: "/checkNickName",
           data: {
               userNickName : nickName,
           },
           success: function success(res){
                if(res === "중복") {
                    swal("이미 사용중인 닉네임입니다.", "", "error")
                } else if (res === "가능") {
                    swal("사용 가능한 닉네임입니다.", "", "success")
                }
           },
           error: function error(err) {

           }
        });
    }
}