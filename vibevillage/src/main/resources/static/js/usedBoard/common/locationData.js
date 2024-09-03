
function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 주소 정보를 전역 변수에 저장
                addressData = data;
                document.getElementById('sample5_address').value = data.address; // 주소 입력 필드에 값 설정
                getCoordinates(data.address); // 주소로 좌표를 구하는 함수 호출

            }
        }).open();
    }



var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption);
//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
//마커를 미리 생성
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});

function getCoordinates(address) {
    geocoder.addressSearch(address, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            console.log("Coordinates for address:", result);
            // 전역 변수에 좌표 저장
            latitude = result[0].y;
            longitude = result[0].x;
            console.log("Latitude:", latitude);
            console.log("Longitude:", longitude);
        } else {
            console.log("Failed to get coordinates for address.");
        }
    });
}

