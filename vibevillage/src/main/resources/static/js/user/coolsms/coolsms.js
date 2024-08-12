function certification() {
    const phone = document.getElementById("phone").value; // 유저가 입력한 연락처
    const certificationArea = document.getElementById("certificationArea"); // 인증 영역

    if(phone === "") {
        swal("연락처를 입력해주세요.", "", "error");
    } else {
        $.ajax({
            type: "POST",
            url: "/sendCertificationNumber",
            data:  {userPhone : phone},
            success: function success(res) {
                swal("전송완료", "3분 이내로 인증해주세요.", "success");
                certificationArea.style.display = "block"; // 숨겨진 인증 영역 보이게 설정
            },
            error: function error(err) {

            },
        });
    }
}

function startCertification() {
    const phone = document.getElementById("phone").value; // 유저가 입력한 연락처
    const certificationInput = document.getElementById("certificationInput").value; // 유저가 입력한 인증번호
    const certificationArea = document.getElementById("certificationArea"); // 인증 영역
    const showText = document.getElementById("showPhone"); // 인증 완료되었을 때 문구나타낼 영역

    // 인증 시작
    $.ajax({
        type: "POST",
        url: "/certification",
        data: {
            certificationInput : certificationInput,
            userPhone : phone,
        },
        success: function success(res) {
            if(res === "인증실패"){
                swal("인증실패", "인증번호를 확인해주세요.", "error");
            } else if (res === "인증성공") {
                swal("인증완료", "", "success");
                certificationArea.style.display = "none"; // 숨겨진 인증 영역 안보이게 설정
                showText.innerText = "인증이 완료되었습니다.";
            }
        },
        error: function error(err) {
        }
    });
}