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