function showTag() {
    const password = document.getElementById('currentPasswordTag');
    const changePassword = document.getElementById('passwordTag');
    const changeRePassword = document.getElementById('rePasswordTag');
    const changeButton = document.getElementById("passwordChangeButton");

    // 토글 기능: 현재 보이는지 여부를 확인하고, 보이면 숨기고, 숨겨져 있으면 보이도록 설정
    if (password.style.display === 'none' || password.style.display === '') {
        password.style.display = 'block';
        changePassword.style.display = 'block';
        changeRePassword.style.display = 'block';
        changeButton.style.display = 'block';
    } else {
        password.style.display = 'none';
        changePassword.style.display = 'none';
        changeRePassword.style.display = 'none';
        changeButton.style.display = 'none';
    }
}