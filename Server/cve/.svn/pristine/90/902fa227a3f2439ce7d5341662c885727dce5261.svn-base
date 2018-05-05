
AC_DEFUN(
 [CHECK_EXE],
 [AC_MSG_CHECKING(for the executable extension)
  EXE_FORMAT=""
  AC_SUBST(EXE_FORMAT)
  AC_MSG_RESULT("a.out" (*nix style))]
)

AC_DEFUN(
 [CHECK_COPY],
 [AC_MSG_CHECKING(for the applicable copy command)
  if test ! -f "/bin/cp" ; then
    COPE_COMMAND="copy"
    AC_MSG_RESULT("copy" (windows style))
  else
    COPY_COMMAND="cp"
    AC_MSG_RESULT("cp" (*nix style))
  fi
  AC_SUBST(COPY_COMMAND)]
)

AC_DEFUN(
 [CHECK_DELETE],
 [AC_MSG_CHECKING(for the applicable delete command)
  if test ! -f "/bin/rm" ; then
    DEL_COMMAND="del"
    AC_MSG_RESULT("del" (windows style))
  else
    DEL_COMMAND="rm -rf"
    AC_MSG_RESULT("rm -rf" (*nix style))
  fi
  AC_SUBST(DEL_COMMAND)]
)
