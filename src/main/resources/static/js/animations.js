gsap.registerPlugin(ScrollTrigger);

gsap.to(".hero-section", {
    scrollTrigger: {
        trigger: ".hero-section",
        start: "top top",
        end: "bottom center",
        scrub: true,
    },
    backgroundPosition: "50% 100%",
    ease: "none"
});
