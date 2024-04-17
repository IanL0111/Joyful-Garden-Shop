var imgEle = document.querySelector("#click");
var menu = document.querySelector("#menu");
var menuWidth = menu.offsetWidth;
var menuMask = document.querySelector("#menuMask");
var menuLeft = document.querySelector(".menuLeft2");
var menuLeftWidth = menuLeft.offsetWidth;


menuMask.addEventListener('click',()=>{
	var currentSrc = imgEle.src;
    var newSrc = (currentSrc.includes('menu')) ? '/image/cross.png' : '/image/menu.png';
    imgEle.src = newSrc;
	menu.style.left = (menu.style.left === '110px' ) ? `-${menuWidth}px` : '110px';
	menuLeft.style.left = (menuLeft.style.left === '0px' ) ? `-${menuLeftWidth}px` : '0px';
	menuMask.classList.remove('open');
})

imgEle.addEventListener('click',()=>{
    var currentSrc = imgEle.src;
    var newSrc = (currentSrc.includes('menu')) ? '/image/cross.png' : '/image/menu.png';
    imgEle.src = newSrc;

    if(menuMask.classList.contains('open')){
        menuMask.classList.remove('open');
		menu.style.left = `-${menuWidth}px`;
		menuLeft.style.left = `-${menuLeftWidth}px`;
	}
    else{
		menuLeft.style.left = '0px';
        menuMask.classList.add('open');
        menu.style.left = '110px';
        }
     
});


document.querySelector('#ul1').addEventListener('mouseover',function(){
	var li = document.querySelector('.li')
	li.style.display = 'block';
});

document.querySelector('#menuOp').addEventListener('mouseleave',function(){
	var li = document.querySelector('.li')
	li.style.display = 'none';
});
