function profile() {
        // querySelector 이용하여 selectFile 변수에 업로드된 파일 주소 초기화
        let selectFile = document.querySelector('#fileName').files[0];
        console.log(selectFile);    

        // createObjectURL 이용하여 파일주소를 file 변수에 초기화
        const file = URL.createObjectURL(selectFile);

        // querySelector 이용하여 img 태그 src 변경
        document.querySelector('#showFile').src = file;
}