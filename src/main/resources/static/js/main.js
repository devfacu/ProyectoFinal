$(document).ready(function () {
    let startMinutes = 5;
    let remainingSeconds = startMinutes * 60;

    var interval = null;

    $("#minutes").text(startMinutes);
    $("#seconds").text("00");


    function start() {
        if (remainingSeconds === 0) return;

        interval = setInterval(() => {
            remainingSeconds --;
            updateTimer();

            if (remainingSeconds === 0) {
                $("#done").text("Session Completed!! Take a Break");
                
            }
        }, 1000);

        updateInterfaceControls();
    }

    function pause() {
        clearInterval(interval);

        interval = null;

        updateInterfaceControls();
    }

    function updateTimer() {
        const minutes = Math.floor(remainingSeconds / 60);
        const seconds = remainingSeconds % 60;

        $("#minutes").text(minutes.toString().padStart(2, "0"));
        $("#seconds").text(seconds.toString().padStart(2, "0"));
    }

    function updateInterfaceControls() {
        if (interval === null) {
            $("#btn-start").html(`<span class="material-icons">play_arrow</span>`);
            $("#btn-start").addClass("timer_btn-start");
            $("#btn-start").removeClass("timer_btn-stop");
        } else {
            $("#btn-start").html(`<span class="material-icons">pause</span>`);
            $("#btn-start").addClass("timer_btn-pause");
            $("#btn-start").removeClass("timer_btn-start");
        }
    }

    $("#btn-start").click(function () {
        if (interval === null) {
            start();
        } else {
            pause();
        }
        
    });

    let checked = false;
    $("#btn-chk").click(function () {
        if (checked) {
            $("#btn-chk").html(`<span class="material-icons md-48">radio_button_unchecked</span>`);
            checked = false;
        } else {
            $("#btn-chk").html(`<span class="material-icons md-48">check_circle_outline</span>`);
            checked = true;
        }
    });
    
});
