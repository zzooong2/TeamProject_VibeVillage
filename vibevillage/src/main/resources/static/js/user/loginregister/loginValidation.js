// let idFlag = false;
// let passwordFlag = false;
//
// function loginIdValidation() {
//     // 유저가 입력한 값
//     const value = document.getElementById("loginId").value;
//     // 검사 결과를 나타낸 영역
//     const showText = document.getElementById("showLoginId");
//     // 정규식
//     const checkId = /^(?=.*[a-z])[A-Za-z\d]{5,}$/;
//
//     if(!checkId.test(value)){
//         failedValidation(showText, "알파벳 대,소문자와 숫자를 조합하여 5자리 이상 작성해주세요.");
//     } else {
//         successValidation(showText);
//         idFlag = true;
//     }
// }
//
// function loginPasswordValidation() {
//     // 유저가 입력한 값
//     const value = document.getElementById('loginPassword').value;
//     // 검사 결과를 나타낼 영역
//     const showText = document.getElementById('showLoginPassword');
//     // 정규식
//     const passwordCheck = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{5,}$/;
//
//     if(!passwordCheck.test(value)) {
//         failedValidation(showText, "특수문자(!@#$%^&*)와 알파벳 대문자 하나씩 포함된 5~20자리 이내 암호여야 합니다.");
//     } else {
//         successValidation(showText)
//         passwordFlag = true;
//     }
// }