jQuery(function($){
    dropHover($(window).width());
    $(window).scroll(function(){
        if ($('.img-index').offset().top >= 340 ) {
            $('.scroll-top').show("slow");
        } else {
            $('.scroll-top').hide("slow");
        }
    });
    $('.scroll-top').on('click', function(event) {
        event.preventDefault();
        $('html, body').animate({scrollTop:0}, "slow");
    });
    jQuery(".pull_feedback").toggle(function(){
            jQuery("#info").animate({right:"0px"});
            return false;
        },
        function(){
            jQuery("#info").animate({right:"-250px"});
            return false;
        }
    );
    $(".scroll_block").sticky({
        topSpacing:0,
        'mode'   : 'animate',
        'speed'   :1
    });
    $(".scroller").sticky({
        topSpacing:52,
        'mode'   : 'animate',
        'speed'   :1
    });
    if ($(window).width() <768){
        $('nav').removeClass('big');
    }


    $(window).resize(function() {
        var resize = $(this).width();
        if (resize < 768) {

            $('nav').removeClass('big');
        }
        if(resize<=984){
            $('.fixed_scroll').hide();
            $(".scroll_block").unstick();
            $(".scroller").unstick();
        }
        else if(resize>984){
            $('.fixed_scroll').show();
            $(".scroll_block").unstick();
            $(".scroller").unstick();
            $(".scroll_block").sticky({
                topSpacing:0,
                'mode'   : 'animate',
                'speed'   :1
            });
            $(".scroller").sticky({
                topSpacing:52,
                'mode'   : 'animate',
                'speed'   :1
            });
        }
        else if(resize>768) {
            $('nav').addClass('big');
            dropHover(resize);
        }
    });
    $('button.pull_feedback').toggle(function(){
        $(this).attr('data-opened',1);
    },function(){
        $(this).attr('data-opened',0);
    });
$('button.pull_feedback').hover(function(){
    if(parseInt($(this).attr('data-opened'))==0){
        $('#info').attr('style','right:-'+$('div#info').width()+'px');
    }
},function(){
    if(parseInt($(this).attr('data-opened'))==0){
        var total = parseInt($(this).width())+parseInt($('div#info').width());
        $('#info').attr('style','right:-'+total+'px');
    }
});
    google.maps.event.addDomListener(window, 'load', initialize);
    $('.left-control').on('click',function(){
        var data_control = parseInt($('ul.testimonials-list').attr('data-slide-item')),
            total = parseInt($('ul.testimonials-list li').length)- 1,new_item=0;
        console.log(data_control,total);
        if(total>0 && data_control>1){
            new_item = data_control-1;
            $('ul.testimonials-list').attr('data-slide-item',new_item);
            $('ul.testimonials-list li:nth-child('+data_control+')').hide();
            $('ul.testimonials-list li:nth-child('+new_item+')').show();
        }

    });
    $('.right-control').on('click',function(){
        var data_control = parseInt($('ul.testimonials-list').attr('data-slide-item')),
            total = parseInt($('ul.testimonials-list li').length),new_item=0;
        if(total>0 && data_control<total){
            new_item = data_control+1;
            $('ul.testimonials-list').attr('data-slide-item',new_item);
            $('ul.testimonials-list li:nth-child('+data_control+')').hide();
            $('ul.testimonials-list li:nth-child('+new_item+')').show();
        }
    });
});


function initialize() {
    var myLatlng = new google.maps.LatLng(50.0586194,14.4681325),
        map = new GMaps({
        el: '#map',
        scrollwheel: false,
        lat: 50.0586194,
        lng: 14.4681325,
        zoom:15
    });
    map.addMarker({
        lat: 50.0586194,
        lng: 14.4681325,
        icon: "images/marker.png"
    });

}




function dropHover(size){
    if (size > 768){
        var theTimer = 0,
            delay = 200,
            theElement = null,
            theLastPosition = {x:0,y:0};
        $('[data-toggle]')
            .closest('li')
            .on('mouseenter', function (inEvent) {
                if (theElement) theElement.removeClass('open');
                window.clearTimeout(theTimer);
                theElement = $(this);

                theTimer = window.setTimeout(function () {
                    theElement.addClass('open');
                }, delay);
            })
            .on('mousemove', function (inEvent) {
                if(Math.abs(theLastPosition.x - inEvent.ScreenX) > 4 ||
                    Math.abs(theLastPosition.y - inEvent.ScreenY) > 4)
                {
                    theLastPosition.x = inEvent.ScreenX;
                    theLastPosition.y = inEvent.ScreenY;
                    return;
                }

                if (theElement.hasClass('open')) return;
                window.clearTimeout(theTimer);
                theTimer = window.setTimeout(function () {
                    theElement.addClass('open');
                }, delay);
            })
            .on('mouseleave', function (inEvent) {
                window.clearTimeout(theTimer);
                theElement = $(this);
                theTimer = window.setTimeout(function () {
                    theElement.removeClass('open');
                }, delay);
            });
    }
}
