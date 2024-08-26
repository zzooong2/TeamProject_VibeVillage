// function login() {
//     // 유저가 입력한 값
//     const inputId = document.getElementById("loginId").value;
//     const inputPwd = document.getElementById("loginPassword").value;
//
//     // inputPwd에 "Tktk"가 포함되어 있다면 임시 비밀번호
//     if (inputPwd.includes("TKtk")) {
//         swal({
//             title: "임시 비밀번호 사용중입니다.",
//             text: "반드시 비밀번호를 변경해주세요.",
//             icon: "warning",
//             button: "확인" // 확인 버튼 텍스트 설정
//         });
//     }
//
//     $.ajax({
//         type: "POST",
//         url: "/login",
//         contentType: "application/json",
//         data: JSON.stringify({
//             userId : inputId,
//             userPassword : inputPwd,
//         }),
//         success: function success(res) {
//             if(res === "로그인 성공") {
//                 swal("로그인 성공", "", "success")
//                     .then(() => {
//                         // 0.1초 후에 로그아웃 페이지로 이동
//                         setTimeout(function() {
//                             window.location.href = "/form";
//                         }, 100);
//                     });
//             } else {
//                 swal("로그인 실패", "계정, 암호를 확인해주세요.", "error");
//             }
//         },
//         error: function error(err) {
//
//         }
//     });
// }


function login() {
    // 유저가 입력한 값
    const inputId = document.getElementById("loginId").value;
    const inputPwd = document.getElementById("loginPassword").value;

    // inputPwd에 "TKtk"가 포함되어 있다면 임시 비밀번호
    if (inputPwd.includes("TKtk")) {
        swal({
            title: "임시 비밀번호 사용중입니다.",
            text: "반드시 비밀번호를 변경해주세요.",
            icon: "warning",
            button: "확인"
        }).then(() => {
            // 0.1초 딜레이 후에 AJAX 요청을 실행
            setTimeout(function() {
                performLogin(inputId, inputPwd);
            }, 100);
        });
    } else {
        // "Tktk"가 포함되지 않았을 경우 바로 로그인 시도
        performLogin(inputId, inputPwd);
    }
}

function performLogin(inputId, inputPwd) {
    $.ajax({
        type: "POST",
        url: "/login",
        contentType: "application/json",
        data: JSON.stringify({
            userId: inputId,
            userPassword: inputPwd,
        }),
        success: function success(res) {
            if (res === "로그인 성공") {
                swal("로그인 성공", "", "success")
                    .then(() => {
                        // 0.1초 후에 메인 페이지로 이동
                        setTimeout(function() {
                            window.location.href = "/form";
                        }, 100);
                    });
            } else {
                swal("로그인 실패", "계정, 암호를 확인해주세요.", "error");
            }
        },
        error: function error(err) {
            swal("에러", "서버 요청 중 문제가 발생했습니다.", "error");
        }
    });
}
