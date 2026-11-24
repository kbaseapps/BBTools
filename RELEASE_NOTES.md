### Version 1.0.4
__Changes__
- Fix RQCFilter hanging indefinitely with human removal on (without microbe removal): pass bloomserial=f so BBMap never tries to write bloom.serial to the read-only /data mount, which deadlocks BBTools 38.73

### Version 1.0.3
__Changes__
- Increasing memory further

### Version 1.0.2
__Changes__
- Modifying to allow greater maxmem

### Version 1.0.1
__Changes__
- Added config options and html results to BBMap
- Update the documentation in the display.yaml file

### Version 1.0.0
__Changes__
- Added BBMap v38.73 (released 2019-11-14)
- updated RQCFilter to v38.73

### Version 0.5.0
__Changes__
- RQCFilter v38.22 supported
