SUMMARY = "QuickBoot CAMX camera frame collection test application"
DESCRIPTION = "Installs a reference camera frame-collection application and an \
opt-in systemd service for QuickBoot camera readiness validation. Captures raw \
frames (NV12 by default) from the CAMX pipeline and retains the last few frames \
on disk (default 10) for boot-time correlation and profiling."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = " \
    file://quickboot-camera-frame-camx \
    file://quickboot-camera-frame-camx.service \
"

S = "${UNPACKDIR}"

inherit systemd features_check

# This application uses the Qualcomm CAMX camera stack and is supported on
# ARMv8 platforms only. Keep it out of the non-ARM layer-check machines.
COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

RDEPENDS:${PN} += " \
    camera-service \
    gstreamer1.0 \
    gst-plugins-imsdk-prop-qticamsrc \
"

SYSTEMD_SERVICE:${PN} = "quickboot-camera-frame-camx.service"

# Ship the test application in the image, but leave it opt-in at runtime.
SYSTEMD_AUTO_ENABLE = "disable"

do_install() {
    install -D -m 0755 ${S}/quickboot-camera-frame-camx \
        ${D}${bindir}/quickboot-camera-frame-camx
    install -D -m 0644 ${S}/quickboot-camera-frame-camx.service \
        ${D}${systemd_system_unitdir}/quickboot-camera-frame-camx.service
}
