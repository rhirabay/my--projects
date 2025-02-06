
from typing import Callable

def test1(callback: Callable[[str], None]):
    callback('test1')

def test2(callback: Callable[[str, bool], None]):
    callback('test2 - true', True)
    callback('test2 - false', False)