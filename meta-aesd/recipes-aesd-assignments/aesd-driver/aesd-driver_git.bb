# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-alighadyali.git;protocol=ssh;branch=master \
	 file://S98aesdchar"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "19ef2f104fb046ecab7fc7df3a0b7b42dc6e1df1"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE  += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-aesdchar"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98aesdchar"
inherit update-rc.d

FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME:${PN}}"

FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load"
FILES:${PN} += "${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${INIT_D_DIR}
    install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME:${PN}} ${D}${INIT_D_DIR}
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
	install -m 755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar.ko
	install -m 755 ${S}/aesdchar_load ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_load
	install -m 755 ${S}/aesdchar_unload ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra/aesdchar_unload
}