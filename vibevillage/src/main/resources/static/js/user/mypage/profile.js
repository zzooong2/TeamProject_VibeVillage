function editNickNameValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('nickName').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showNickName');
    // 정규식
    const checkNickName = /^[가-힣a-zA-Z0-9]{1,20}$/;

    if(!checkNickName.test(value)) {
        failedValidation(showText, "특수문자 제외, 한글 + 영어(대,소문자) + 숫자 조합(20자리 이내)으로 작성해주세요.");
    } else {
        successValidation(showText);
        nickNameFlag = true;
    }
}


let checkEditNickNameFlag = false;

// 닉네임 중복검사
function checkEditNickName() {
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
                    checkEditNickNameFlag = true;
                    swal("사용 가능한 닉네임입니다.", "", "success")
                }
            },
            error: function error(err) {

            }
        });
    }
}

// 업로드파일 미리보기
function profile() {
        // querySelector 이용하여 selectFile 변수에 업로드된 파일 주소 초기화
        let selectFile = document.querySelector('#fileName').files[0];
        console.log(selectFile);    

        // createObjectURL 이용하여 파일주소를 file 변수에 초기화
        const file = URL.createObjectURL(selectFile);

        // querySelector 이용하여 img 태그 src 변경
        document.querySelector('#showFile').src = file;
}

// 파일 업로드
function uploadProfile() {
        const uploadFileElement = document.getElementById("fileName").files[0]; // 파일 객체를 가져옴

    // 파일이 선택되지 않았을 경우 처리
        if (!uploadFileElement) {
            swal("파일을 선택해주세요.", "", "info");
            return; // 함수 종료
        }

        const fullPath = uploadFileElement.name;
        const uploadFileName = fullPath.split('\\').pop().split('/').pop();  // 파일명 추출
        const uploadFileType = getFileExtension(uploadFileName); // 확장자 추출


        // FormData 객체 생성
        const formData = new FormData();
        formData.append("uploadFileName", uploadFileName); // 파일명 추가
        formData.append("uploadFileType", uploadFileType); // 파일타입 추가
        formData.append("uploadFileElement", uploadFileElement); // 실제 파일 추가

        $.ajax({
           type: "POST",
           url: "/uploadProfile",
           processData: false,
           contentType: false,
           data: formData,
           success: function success(res) {
                   if(res === "업로드 성공") {
                           swal("업로드 성공", "", "success");
                   } else if (res === "기존파일 삭제실패") {
                           swal("기존 프로필 삭제에 실패했습니다. ", "관리자에게 문의해주세요.", "error");
                   } else {
                           swal("업로드 실패", "", "error");
                   }
           },
           error: function error(err) {

           }
        });
}

// .을 기준으로 확장자 추출하는 함수
function getFileExtension(filename) {
        // 파일 이름에서 마지막 '.'의 위치 찾기
        const lastDotIndex = filename.lastIndexOf(".");

        // '.'이 존재하지 않으면 빈 문자열 반환
        if (lastDotIndex === -1) return "";

        // '.' 뒤에 있는 문자열(확장자) 반환
        return filename.substring(lastDotIndex + 1);
}

// 회원정보 수정
function editProfile() {
    // 유저가 입력한 값
    const editName = document.getElementById("userName").value;
    const editBirthDate = document.getElementById("birthDate").value;
    const editNickName = document.getElementById("nickName").value;
    const editPhone = document.getElementById("phone").value;
    const editPostCode = document.getElementById("postCode").value;
    const editAddress = document.getElementById("address").value;
    const editDetailAddress = document.getElementById("detailAddress").value;
    const editExtraAddress = document.getElementById("extraAddress").value;


    // 정보 변경유무 파악을 위한 변수
    const originalName = document.getElementById("originalUserName").value;
    const originalBirthDate = document.getElementById("originalBirthDate").value;
    const originalNickName = document.getElementById("originalNickName").value;
    const originalPhone = document.getElementById("originalPhone").value;
    const originalPostCode = document.getElementById("originalPostCode").value;
    const originalAddress = document.getElementById("originalAddress").value;
    const originalDetailAddress = document.getElementById("originalDetailAddress").value;
    const originalExtraAddress = document.getElementById("originalExtraAddress").value;

    // 변경 여부 확인
    if (editName === originalName && editBirthDate === originalBirthDate && editNickName === originalNickName && editPhone === originalPhone && editPostCode === originalPostCode && editAddress === originalAddress && editDetailAddress === originalDetailAddress && editExtraAddress === originalExtraAddress) {
        swal("변경된 정보가 없습니다.", "", "info");
        return; // 함수 종료, 서버로 요청을 보내지 않음
    } else if (editNickName !== originalNickName) {
        if(checkEditNickNameFlag === false){
            swal("닉네임 중복검사를 진행해주세요.", "", "info");
            return;
        }
    }

    $.ajax({
        type: "POST",
        url: "/editProfile",
        data: {
            userName : editName,
            userBirthDate : editBirthDate,
            userNickName : editNickName,
            userPhone : editPhone,
            userPostCode : editPostCode,
            userAddress : editAddress,
            userDetailAddress : editDetailAddress,
            userExtraAddress : editExtraAddress,
        },
        success: function success(res) {
            if(res === "성공") {
                swal("회원정보 수정이 완료되었습니다.", "", "success");
            } else {
                swal("회원정보 수정에 실패했습니다.", "관리자에게 문의해주세요.", "error");
            }
        },
        error: function error(err) {

        }
    });

}

// 비밀번호 변경
function editPassword() {
    // 현재 암호
    const currentPassword = document.getElementById("currentPassword").value;
    // 변경할 암호
    const editPassword = document.getElementById("password").value;
    // 암호 확인
    const editRePassword = document.getElementById("rePassword").value;

    // 값 입력 확인
    if(currentPassword === "" || editPassword === "" || editRePassword === ""){
        swal("현재, 변경 비밀번호를 반드시 입력해주세요.", "", "info");
        return;
    }

    if(currentPassword == editPassword) {
        swal("현재 비밀번호로는 변경할 수 없습니다.", "", "error");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/editPassword",
        data: {
            currentPassword : currentPassword,
            userPassword : editPassword,
            userRePassword : editRePassword
        },
        success: function success(res) {
            if(res === "비밀번호를 입력해주세요.") {
                swal(res, "", "info");
            } else if(res === "현재 비밀번호가 일치하지 않습니다.") {
                swal(res, "", "error");
            } else if(res === "성공") {
                swal("비밀번호 변경을 완료했습니다.", "변경된 비밀번호로 다시 로그인해주세요.", "success")
                .then(() => {
                        // 0.1초 후에 로그아웃 페이지로 이동
                        setTimeout(function() {
                            window.location.href = "/logout";
                        }, 100);
                    });
            } else if(res === "실패"){
                swal("비밀번호 변경에 실패했습니다.", "관리자에게 문의해주세요.", "error");
            } else if(res === "현재 비밀번호로는 변경할 수 없습니다."){
                swal(res, "", "error");
            }
        },
    });
}