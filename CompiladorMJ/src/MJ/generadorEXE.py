
# Este archivo sirve para generar el archivo .exe del compilador con la
# herramienta py2exe

try:
    from distutils.core import setup
    import py2exe
    setup(console=['mj.py'])
except BaseException:
    print 'Para usar este script debe tener py2exe instalado\nbajelo en la pagina: http://www.py2exe.org/'