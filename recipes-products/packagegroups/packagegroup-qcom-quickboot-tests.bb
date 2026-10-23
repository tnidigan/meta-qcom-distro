SUMMARY = "Qualcomm QuickBoot validation workload package group"
DESCRIPTION = "Package group containing QuickBoot workloads used to validate camera media readiness."

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"

# The CAMX camera workloads are available only on ARMv8 platforms.
RDEPENDS:${PN}:append:aarch64 = " \
    quickboot-camera-frame-camx \
"