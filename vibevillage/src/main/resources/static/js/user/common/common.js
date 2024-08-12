// 정규식과 상이할 때 출력하는 메세지
function failedValidation(showText, text) {
        showText.style.display="block";
        showText.style.color="red";
        showText.innerText = text;
}

// 정규식과 동일할 때 출력하는 메세지
function successValidation(showText) {
        showText.style.display="none";
}