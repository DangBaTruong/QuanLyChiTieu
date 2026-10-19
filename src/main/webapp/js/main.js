// ===== Modal sửa giao dịch (dùng ở giaodich.jsp) =====
function moModalSua(id, danhMucId, loai, soTien, ngay, ghiChu) {
    document.getElementById('modal-id').value = id;
    document.getElementById('modal-danhMucId').value = danhMucId;
    document.getElementById('modal-loai').value = loai;
    document.getElementById('modal-soTien').value = soTien;
    document.getElementById('modal-ngay').value = ngay;
    document.getElementById('modal-ghiChu').value = ghiChu;
    document.getElementById('modal-sua-giao-dich').classList.add('open');
}

function dongModalSua() {
    document.getElementById('modal-sua-giao-dich').classList.remove('open');
}

// ===== Trang "Tôi": thông tin cá nhân lưu ở localStorage (không cần thêm cột DB) =====
function taiThongTinCaNhan() {
    var gioiThieu = localStorage.getItem('qlct_gioiThieu') || '';
    var soDienThoai = localStorage.getItem('qlct_soDienThoai') || '';
    var diaChi = localStorage.getItem('qlct_diaChi') || '';

    var elGioiThieu = document.getElementById('input-gioi-thieu');
    var elSoDienThoai = document.getElementById('input-so-dien-thoai');
    var elDiaChi = document.getElementById('input-dia-chi');

    if (elGioiThieu) elGioiThieu.value = gioiThieu;
    if (elSoDienThoai) elSoDienThoai.value = soDienThoai;
    if (elDiaChi) elDiaChi.value = diaChi;
}

function luuThongTinCaNhan() {
    var gioiThieu = document.getElementById('input-gioi-thieu').value;
    var soDienThoai = document.getElementById('input-so-dien-thoai').value;
    var diaChi = document.getElementById('input-dia-chi').value;

    localStorage.setItem('qlct_gioiThieu', gioiThieu);
    localStorage.setItem('qlct_soDienThoai', soDienThoai);
    localStorage.setItem('qlct_diaChi', diaChi);

    var thongBao = document.getElementById('thong-bao-luu');
    if (thongBao) {
        thongBao.style.display = 'inline';
        setTimeout(function () { thongBao.style.display = 'none'; }, 2000);
    }
}
