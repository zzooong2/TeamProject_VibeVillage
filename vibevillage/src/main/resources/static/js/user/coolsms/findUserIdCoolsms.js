let checkCertificationById = document.getElementById("checkCertificationById");
let checkCertificationByPassword = document.getElementById("checkCertificationByPassword");

// 연락처로 회원 유무 확인 (계정 찾기)
function checkUserInfoByPhone() {
    const userPhone = document.getElementById("UserPhoneByFindId").value; // 유저가 입력한 연락처

    if(userPhone === "") {
        swal("연락처를 입력해주세요.", "", "info");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/checkUserInfoByPhone",
        data: { userPhone: userPhone },
        success: function success(res) {
            if(res !== 0) {
                certificationByFindId();
            } else if(res === 0){
                swal("회원정보가 없습니다.", "연락처를 확인해주세요.", "error");
            }
        }
    });
}

// 연락처로 회원 유무 확인 (비밀번호 찾기)
function checkUserInfoByPhone2() {
    const userPhone = document.getElementById("UserPhoneByFindPassword").value; // 유저가 입력한 연락처

    if(userPhone === "") {
        swal("연락처를 입력해주세요.", "", "info");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/checkUserInfoByPhone",
        data: { userPhone: userPhone },
        success: function success(res) {
            if(res !== 0) {
                certificationByFindPassword();
            } else if(res === 0){
                swal("회원정보가 없습니다.", "연락처를 확인해주세요.", "error");
            }
        }
    });
}


// ------------------인증번호 발송 (계정 찾기)
function certificationByFindId() {
    const userPhone = document.getElementById("UserPhoneByFindId").value; // 유저가 입력한 연락처
    const certificationArea = document.getElementById("certificationAreaByFindId"); // 인증 영역

        $.ajax({
            type: "POST",
            url: "/sendCertificationNumber",
            data:  {userPhone : userPhone},
            success: function success(res) {
                swal("전송완료", "3분 이내로 인증해주세요.", "success");
                certificationArea.style.display = "block"; // 숨겨진 인증 영역 보이게 설정
                console.log("인증영역 :" + checkCertificationById.innerText);
            },
            error: function error(err) {

            },
        });

}

// ------------------인증번호 발송 (비밀번호 찾기)
function certificationByFindPassword() {
    const userPhone = document.getElementById("UserPhoneByFindPassword").value; // 유저가 입력한 연락처
    const certificationArea = document.getElementById("certificationAreaByFindPassword"); // 인증 영역

    $.ajax({
        type: "POST",
        url: "/sendCertificationNumber",
        data:  {userPhone : userPhone},
        success: function success(res) {
            swal("전송완료", "3분 이내로 인증해주세요.", "success");
            certificationArea.style.display = "block"; // 숨겨진 인증 영역 보이게 설정
            console.log("인증영역 :" + checkCertificationByPassword.innerText);
        },
        error: function error(err) {

        },
    });

}

// ------------인증코드 정합성 확인 (계정찾기)
function startCertificationByFindId() {
    const phone = document.getElementById("UserPhoneByFindId").value; // 유저가 입력한 연락처
    const certificationInput = document.getElementById("certificationInputByFindId").value; // 유저가 입력한 인증번호
    const certificationArea = document.getElementById("certificationAreaByFindId"); // 인증 영역
    const showText = document.getElementById("showPhoneByFindId"); // 인증 완료되었을 때 문구 나타낼 영역
    console.log("인증코드 문자 발송 함수 실행")
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
                console.log("인증코드 문자 발송 함수 실행");
                swal("인증실패", "인증번호를 확인해주세요.", "error");
            } else if (res === "인증성공") {
                swal("인증완료", "", "success");
                certificationArea.style.display = "none"; // 숨겨진 인증 영역 안보이게 설정
                showText.innerText = "인증이 완료되었습니다.";
                checkCertificationById.innerText = "인증완료"; // 인증이 성공했을 때 checkFlag에 "인증완료" 추가
                console.log("인증영역 :" + checkCertificationById.innerText);
            }
        },
        error: function error(err) {
        }
    });
}

// ------------인증코드 정합성 확인 (비밀번호 찾기)
function startCertificationByFindPassword() {
    const phone = document.getElementById("UserPhoneByFindPassword").value; // 유저가 입력한 연락처
    const certificationInput = document.getElementById("certificationInputByFindPassword").value; // 유저가 입력한 인증번호
    const certificationArea = document.getElementById("certificationAreaByFindPassword"); // 인증 영역
    const showText = document.getElementById("showPhoneByFindPassword"); // 인증 완료되었을 때 문구 나타낼 영역

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
                checkCertificationByPassword.innerText = "인증완료"; // 인증이 성공했을 때 checkFlag에 "인증완료" 추가
                console.log("인증영역 :" + checkCertificationByPassword.innerText);
            }
        },
        error: function error(err) {
        }
    });
}