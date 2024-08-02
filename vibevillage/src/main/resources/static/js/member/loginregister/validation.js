function nameValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('userName').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showName');
    // 정규식
    let checkKor = /^[ㄱ-ㅎ가-힣]+$/;

    if(!checkKor.test(value)) {
        // common.js 파일에 선언해둔 함수
        failedValidation(showText, "문자(한글)만 입력가능합니다.");
    } else {
        showText.style.display="none";
    }
}

function birthDateValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('birthDate').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showBirthDate');
    // 정규식
    const checkBirthDate = /^(19[0-9]{2}|20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$/;

    // 현재 연도 이하인지 추가 검증
    const currentYear = new Date().getFullYear();

    function isValidDate(dateString) {
            if (!checkBirthDate.test(dateString)) {
                return false;
            }
        const year = parseInt(dateString.substring(0, 4), 10);
        return year <= currentYear;
    }
    if(!isValidDate(value)){
        failedValidation(showText, "생년월일 8자리를 입력해주세요.");
    } else {
        successValidation(showText);
    }
}

function emailValidation() {
    // 유저가 입력한 값
    const value = document.getElementById("email").value;
    // 검사 결과를 나타낼 영역
    const showText =  document.getElementById('showEmail');
    // 정규식
    const checkEmail = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z0-9]{5,20}$/;

    if(!checkEmail.test(value)) {
        failedValidation(showText, "5~20자리의 알파벳 대, 소문자와 숫자만 사용 가능합니다.");
    } else {
        successValidation(showText);
    }
}

function passwordValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('password').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showPassword');
    // 정규식
    const passwordCheck = /^(?=.*[A-Z])(?=.*[!@#$%^&*])[A-Za-z!@#$%^&*]{5,20}$/;

    if(!passwordCheck.test(value)) {
        failedValidation(showText, "특수문자(!@#$%^&*)와 알파벳 대문자 하나씩 포함된 5~20자리 이내 암호여야 합니다.");
    } else { 
        successValidation(showText)
    }
}

function rePasswordValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('password').value;
    const reValue = document.getElementById('rePassword').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showRePassword');

    if(value !== reValue) {
        failedValidation(showText, "암호가 일치하지 않습니다.");
    } else { 
        successValidation(showText);
    }
}

function nickNameValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('nickName').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showNickName');
}

function phoneValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('phone').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showPhone');
}


