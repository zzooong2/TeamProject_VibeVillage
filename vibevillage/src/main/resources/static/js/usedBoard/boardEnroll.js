const submitButton = document.getElementById('submit-button');

submitButton.addEventListener('click', function() {
    const formData = new FormData();
    const fields = [
        { key: 'usedBoardTitle', value: document.getElementById('usedBoardTitle').value, message: '제목을 입력해주세요' },
        { key: 'usedBoardProductName', value: document.getElementById('usedBoardProductName').value, message: '물품을 입력해주세요' },
        { key: 'usedBoardProductPrice', value: document.getElementById('usedBoardProductPrice').value, message: '가격을 입력해주세요' },
        { key: 'usedBoardContent', value: document.getElementById('usedBoardContent').value, message: '내용을 입력해주세요' },
        { key: 'usedBoardLocation', value: document.getElementById('sample5_address').value, message: '주소를 입력해주세요' },
        { key: 'categoryId', value: document.getElementById('categoryId').value, message: '카테고리를 선택해주세요' },
        { key: 'province', value: addressData.sido, message: '주소를 입력해주세요' },
        { key: 'city', value: addressData.sigungu, message: '주소를 입력해주세요' },
        { key: 'gpsLatitude', value: latitude, message: 'Latitude is required' },
        { key: 'gpsLongitude', value: longitude, message: 'Longitude is required' }
    ];

    for (const field of fields) {
        if (!field.value) {
            swal(field.message, "", "error");   
            return;
        }
        formData.append(field.key, field.value);
    }

    if (mainImage) {
        formData.append('mainFile', mainImage);
    }
    previewImages.forEach(image => {
        formData.append('previewFiles', image);
    });

    fetch('/used/boardEnroll', {
        method: 'POST',
        body: formData,
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        swal(data.message, "", "success");
        if (data.message === "글쓰기 성공") {
            window.location.href = "/used/boardList/1";
        }
    })
    .catch(error => {
        swal(error,"","error");
        alert('Upload failed!');
    });
});
