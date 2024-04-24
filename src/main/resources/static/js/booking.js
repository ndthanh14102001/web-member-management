function openModal(deviceId) {
    var currentDateTime = new Date().toISOString().slice(0, 16);
    document.getElementById('modalBookingTime').min = currentDateTime;
    document.getElementById('deviceId').value = deviceId;
    var modal = new bootstrap.Modal(document.getElementById('bookingModal'));
    modal.show();
}

function submitForm() {
    var selectedDateTime = new Date(document.getElementById('modalBookingTime').value);
    var minimumDateTime = new Date();
    minimumDateTime.setMinutes(minimumDateTime.getMinutes() + 30);
    if (selectedDateTime < minimumDateTime) {
        alert('Thời gian đặt chỗ phải sau ít nhất 30 phút so với thời gian hiện tại');
        return;
    }
    document.getElementById('bookingForm').submit();
}