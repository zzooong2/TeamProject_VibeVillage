let nameFlag = false;
let birthDateFlag = false;
let idFlag = false;
let passwordFlag = false;
let rePasswordFlag = false;
let nickNameFlag = false;
let phoneFlag = false;
let postCodeFlag = false;
let detailAddressFlag = false;
let checkIdFlag = false;
let checkNickNameFlag = false;

function idValidation() {
    // 유저가 입력한 값
    const value = document.getElementById("userId").value;
    // 검사 결과를 나타낸 영역
    const showText = document.getElementById("showId");
    // 정규식
    const checkId = /^(?=.*[a-z])[A-Za-z\d]{5,}$/;

    if(!checkId.test(value)){
        failedValidation(showText, "알파벳 대,소문자와 숫자를 조합하여 5자리 이상 작성해주세요.");
    } else {
        successValidation(showText);
        idFlag = true;
    }
}

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
        successValidation(showText);
        nameFlag = true;
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
        birthDateFlag = true;
    }
}

function passwordValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('password').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showPassword');
    // 정규식
    const passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{5,}$/;

    if(!passwordCheck.test(value)) {
        failedValidation(showText, "특수문자(!@#$%^&*)와 알파벳 대문자 하나씩 포함된 5~20자리 이내 암호여야 합니다.");
    } else { 
        successValidation(showText)
        passwordFlag = true;
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
        rePasswordFlag = true;
    }
}

function nickNameValidation() {
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

function phoneValidation() {
    // 유저가 입력한 값
    const value = document.getElementById('phone').value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById('showPhone');
    // 정규식
    const checkPhone = /^\d{11}$/;

    if(!checkPhone.test(value)){
        failedValidation(showText, "연락처(-제외)만 입력해주세요.");
    } else {
        successValidation(showText);
        phoneFlag = true;
    }
}

function postCodeValidation() {
    // 입력한 값
    const postCode = document.getElementById("postCode").value;
    const detailAddress = document.getElementById("detailAddress").value;
    // 검사 결과를 나타낼 영역
    const showText = document.getElementById("showAddress");

    if(postCode === "" && detailAddress === "") {
        failedValidation(showText, "주소를 입력해주세요.");
    } else {
        successValidation(showText);
        postCodeFlag = true;
        detailAddressFlag = true;
    }
}

// 계정 중복검사
function checkId() {
    console.log("checkId 메소드 실행");
    const userId = document.getElementById("userId").value;
    console.log("?????")
    console.log(userId)

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
                    checkIdFlag = true;
                    swal("사용 가능한 계정입니다.", "", "success")
                }
            },
            error: function error(err) {
                swal("오류 발생", "서버와의 통신 중 오류가 발생했습니다.", "error");
            }
        });
    }
}

// 닉네임 중복검사
function checkNickName() {
    console.log("checkNickName 메소드 실행");
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
                    checkNickNameFlag = true;
                    swal("사용 가능한 닉네임입니다.", "", "success")
                }
            },
            error: function error(err) {

            }
        });
    }
}

function checkCertification() {
    // 인증번호 입력 값
    const certification = document.getElementById("certification").value;

}


// 회원가입
function register() {
    const userName = document.getElementById("userName").value;
    const userBirthDate = document.getElementById("birthDate").value;
    const userId = document.getElementById("userId").value;
    const userPassword = document.getElementById("password").value;
    const userNickName = document.getElementById("nickName").value;
    const userPhone = document.getElementById("phone").value;
    const userPostCode = document.getElementById("postCode").value;
    const userAddress = document.getElementById("address").value;
    const userDetailAddress = document.getElementById("detailAddress").value;
    const userExtraAddress = document.getElementById("extraAddress").value;

    console.log("userName: " + userName);
    console.log("userBirthDate: " + userBirthDate);
    console.log("userId: " + userId);
    console.log("userPassword: " + userPassword);
    console.log("userNickName: " + userNickName);
    console.log("userPhone: " + userPhone);
    console.log("userPostCode: " + userPostCode);
    console.log("userAddress: " + userAddress);
    console.log("userDetailAddress: " + userDetailAddress);
    console.log("userExtraAddress: " + userExtraAddress);

    if(nameFlag && birthDateFlag && idFlag && passwordFlag && rePasswordFlag && nickNameFlag && phoneFlag && postCodeFlag && detailAddressFlag){
        if(checkIdFlag && checkNickNameFlag) {
            $.ajax({
                type: "POST",
                url: "/register",
                data: {
                    userName : userName,
                    userBirthDate : userBirthDate,
                    userId : userId,
                    userPassword : userPassword,
                    userNickName : userNickName,
                    userPhone : userPhone,
                    userPostCode : userPostCode,
                    userAddress : userAddress,
                    userDetailAddress : userDetailAddress,
                    userExtraAddress : userExtraAddress,
                },
                success: function success(res) {
                    if(res === "유효성 검사 오류") {
                        swal("오류 발생", "안내문구에 맞게 회원정보를 입력해주세요." , "error");
                    } else if(res === "성공") {
                        swal("회원가입 성공", "반갑습니다." , "success");
                        // alert창 띄운 후 1.5초 뒤 새로고침 실행
                        setTimeout(function() {
                            document.location.reload();
                        },1500);
                    } else {
                        swal("회원가입 실패", "입력하신 정보를 확인해주세요.", "error");
                    }
                },
                error: function error(err) {
                }
            });
        } else {
            swal("계정, 닉네임에 대한 중복검사를 진행해주세요.", "", "error");
        }
    } else {
        swal("회원정보를 모두 입력해주세요.", "", "error");
    }
}
