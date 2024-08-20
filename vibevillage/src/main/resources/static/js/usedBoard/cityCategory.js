function sendCategoryValue() {
    const selectedCategory = document.getElementById("category").value;
    var province = document.getElementById("province").value;
    var citySelect = document.getElementById("city").value;
    const url = `/used/boardList/1?category=${selectedCategory}&province=${province}&citySelect=${citySelect}`;
    window.location.href = url;
}
function updateCitySelect() {
    var province = document.getElementById("province").value;
    var citySelect = document.getElementById("city");

    // 시/군 선택 항목 초기화
    citySelect.innerHTML = '<option value="">시/군 선택</option>';

    // 시/군 데이터 객체
    var cities = {
        "서울": ["종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"],
        "부산": ["중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군"],
        "대구": ["중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"],
        "인천": ["중구", "동구", "미추홀구", "연수구", "남동구", "부평구", "계양구", "서구", "강화군", "옹진군"],
        "경기": ["수원시", "성남시", "고양시", "용인시", "부천시", "안산시", "안양시", "남양주시", "화성시", "평택시", "의정부시", "시흥시", "파주시", "광명시", "김포시", "군포시", "광주시", "이천시", "양주시", "오산시", "구리시", "안성시", "포천시", "의왕시", "하남시", "여주시", "양평군", "동두천시", "가평군", "연천군"]
        // 추가적인 도와 시/군을 필요에 따라 추가하세요.
    };

    // 선택된 도에 맞는 시/군 옵션 추가
    if (province && cities[province]) {
        cities[province].forEach(function(city) {
            var option = document.createElement("option");
            option.value = city;
            option.text = city;
            citySelect.add(option);
        });
    }
}