
function deleteCurrentMainImage(item) {
    const main = document.getElementById("currentMain");
    if (main) {
        deleteList.push(item);  // 삭제 리스트에 추가
        main.remove();  // 화면에서 이미지 제거
    }
}

function deleteCurrentSubImage(item) {
    const subImage = document.querySelector(`img[name="${item}"]`);
    if (subImage) {
        deleteList.push(item);  // 삭제 리스트에 추가
        // item의 인덱스를 찾은 후, 해당 인덱스의 요소를 배열에서 제거
        const index = previewImages.indexOf(item);
        if (index > -1) {  // item이 배열에 존재하는 경우
            previewImages.splice(index, 1);
        }
        subImage.closest('li').remove();  // 화면에서 이미지 제거
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.getElementById('submit-button');

    submitButton.addEventListener('click', function () {
        const formData = new FormData();
        formData.append('usedBoardId', boardId);
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

        // 필수 필드가 채워져 있는지 확인
        for (const field of fields) {
            if (!field.value) {
                swal(field.message, "", "error");
                return;
            }
            formData.append(field.key, field.value);
        }

        // 서브 이미지가 3개 이하인지 확인
        if ((previewImages.length+previousFile)-deleteList.length > 3) {
            swal('이미지는 최대 3개까지 업로드할 수 있습니다.', "", "error");
            return;
        }
        if ((previewImages.length+previousFile)-deleteList.length == 0) {
            swal('이미지를 최소 한개 이상 업로드 해주세요', "", "error");
            return;
        }

        formData.append('mainFile', mainImage);
        previewImages.forEach(image => {
            formData.append('previewFiles', image);
        });
        formData.append('deleteList', deleteList);


        fetch('/used/update', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())
            .then(data => {
                swal({
                    title: data.message,
                    icon: "success",
                    buttons: "OK"
                }).then((value) => {
                    if (value && data.message === "수정 성공") {
                        window.location.href = "/used/boardList/1";
                    }
                });
            })
            .catch(error => {
                swal({
                    title: 'Error',
                    text: error,
                    icon: 'error',
                    buttons: "OK"
                });
            });
    });
});