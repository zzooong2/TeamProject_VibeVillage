const checkFlagByFindId = document.getElementById("checkCertificationById");
const checkFlagByFindPassword = document.getElementById("checkCertificationByPassword");


function findUserId() {
    // 회원 이름
    const inputName = document.getElementById("inputName").value;
    // 회원 연락처
    const inputPhone = document.getElementById("UserPhoneByFindId").value;

    if(inputName === "") {
        swal("회원정보(이름)를 입력해주세요.", "", "info");
        return;
    }

    if(checkFlagByFindId.innerText === "인증완료") {
        $.ajax({
            type: "POST",
            url: "/findUserId",
            data: JSON.stringify({
                userName: inputName,
                userPhone: inputPhone,
            }),
            contentType: "application/json",
            success: function(res) {
                if (res && res.userId) {
                    swal("계정 찾기 성공", "회원님의 계정: " + res.userId, "success");
                } else {
                    swal("계정 찾기 실패", "입력하신 정보와 일치하는 계정을 찾을 수 없습니다.", "error");
                }
            },
            error: function() {
                swal("에러", "서버 요청 중 문제가 발생했습니다.", "error");
            }
        });
    } else {
        swal("본인인증을 먼저 진행해주세요.", "", "info");
    }
}

// 비밀번호 찾기
function findUserPassword() {
    // 회원 계정
    const inputId = document.getElementById("inputId").value;
    // 회원 연락처
    const inputPhone = document.getElementById("UserPhoneByFindPassword").value;

    if(inputId === "") {
        swal("회원정보(계정)를 입력해주세요.", "", "info");
        return;
    }

    if(checkFlagByFindPassword.innerText === "인증완료") {
        $.ajax({
            type: "POST",
            url: "/findUserPassword",
            data: JSON.stringify({
                userId: inputId,
                userPhone: inputPhone,
            }),
            contentType: "application/json",
            success: function(res) {
                if (res && res.userPassword) {
                    swal("임시비밀번호 발급: " + res.userPassword, "반드시 비밀번호를 변경해주세요.", "success");
                } else {
                    swal("실패", "입력하신 정보와 일치하는 정보를 찾을 수 없습니다.", "error");
                }
            },
            error: function() {
                swal("에러", "서버 요청 중 문제가 발생했습니다.", "error");
            }
        });
    } else {
        swal("본인인증을 먼저 진행해주세요.", "", "info");
    }
}